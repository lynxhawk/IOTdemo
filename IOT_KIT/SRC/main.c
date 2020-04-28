#include "LIB_Config.h"
#include "Hal_rgb_led.h"
#include "Hal_uart.h"
#include "EMSP_API.h"
#include "em380c_hal.h"
#include "hal_temp_hum.h"
#include "hal_infrared.h"
#include "stm32f4xx_rng.h"
#include "hal_motor.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>	
#include <hal_key.h>
#define	STARTFLAG	0xABABABAB
#define NUM 0x31602377

typedef struct {
	unsigned char ID;
	unsigned int V1;
	unsigned int V2;
	unsigned int V3;
	unsigned short VNot;
}__attribute__((packed)) sensor_data_frame;

typedef struct {
	unsigned char START_F0;
	unsigned char START_F1;
	unsigned char START_F2;
	unsigned char START_F3;
	unsigned char num1;
	unsigned char num2;
	unsigned char num3;
	unsigned char num4;
	unsigned char sensor_num;
	unsigned short  heartbeat;
	sensor_data_frame sensor_dev1;
	sensor_data_frame sensor_dev2;
	sensor_data_frame sensor_dev3;
	unsigned short chkorl;
}__attribute__((packed)) device_comm_frame;

typedef struct {
	unsigned char id;
	unsigned int V1;
	unsigned int V2;
	unsigned int V3;
	unsigned short VNot;
}__attribute__((packed)) receive_sensor_data;

typedef struct {
	unsigned char START_F0;
	unsigned char START_F1;
	unsigned char START_F2;
	unsigned char START_F3;
	unsigned char num1;
	unsigned char num2;
	unsigned char num3;
	unsigned char num4;
	unsigned char sensor_num;
	unsigned short heartbeat;

	receive_sensor_data sensor_dev1;
	receive_sensor_data sensor_dev2;
	receive_sensor_data sensor_dev3;
	
	unsigned short chkorl;
}__attribute__((packed)) receive_IOT;


extern bool bInfrared;


char const SERVER_IP[] = { "192.168.43.22" }; /* ??IP?? */
char const GATEWAY_IP[] = { "192.168.43.88" };
char const WIFI_SSID[] = { "Lordran" };
char const WIFI_KEY[] = { "tx980616" };
//char const WIFI_KEY[] = { "1234567890" };
uint16_t SERVER_PORT = 12234;

char const LOCAL_IP[] = { "192.168.43.0" };
//char const LOCAL_IP[] = { "10.64.133.0" }; /* ??IP?? */

extern int buff_pos;

u8 rcv_buf_from_server[256];
receive_sensor_data sensor_data[3];

/* Private variables ---------------------------------------------------------*/
EM380C_parm_TypeDef parm;
u32 version;

void setup_wifi(void)
{       
	EMW3612_CMD;
	delay_ms(200);
	
	LED_RGB_Control(0, 0, 10);
	delay_ms(200);
	EM380C_Init(BaudRate_115200, WordLength_8b, StopBits_1, Parity_No,
									HardwareFlowControl_None, buffer_128bytes); //if you dont know EM380C's baudrate,you should use EM380C_scan_Init(...)
	delay_ms(400);
	delay_ms(400);

	while (EM380C_Get_ver(&version) == EM380ERROR);
//ret = EM380C_Get_ver(&version);
	LED_RGB_Control(10, 0, 0);
	delay_ms(200);

	parm.wifi_mode = STATION;
	strcpy((char*) parm.wifi_ssid, WIFI_SSID);
	strcpy((char*) parm.wifi_wepkey, WIFI_KEY);
	parm.wifi_wepkeylen = strlen(WIFI_SSID);

	strcpy((char*) parm.local_ip_addr, LOCAL_IP);
	strcpy((char*) parm.remote_ip_addr, SERVER_IP);
	strcpy((char*) parm.gateway, GATEWAY_IP);
	strcpy((char*) parm.net_mask, "255.255.255.0");
	parm.portH = SERVER_PORT >> 8;
	parm.portL = SERVER_PORT & 0xff;
	parm.connect_mode = TCP_Client;
	parm.use_dhcp = DHCP_Enable;
	parm.use_udp = TCP_mode;
	parm.UART_buadrate = BaudRate_115200;
	parm.DMA_buffersize = buffer_64bytes;
	parm.use_CTS_RTS = HardwareFlowControl_None;
	parm.parity = Parity_No;
	parm.data_length = WordLength_8b;
	parm.stop_bits = StopBits_1;

	parm.io1 = None;
	strcpy((char*) parm.wpa_key, WIFI_KEY);
	parm.security_mode = Auto;

	while (EM380C_Set_Config(&parm) == EM380ERROR)
					;                                                                        //EMSP API, set EM380C status
	delay_ms(400);
	LED_RGB_Control(0, 10, 0);

	while (EM380C_Startup() == EM380ERROR)
					;
	delay_ms(400);
	LED_RGB_Control(5, 5, 5);

//EM380C_Reset(); 
	delay_ms(400);  
	EMW3612_DAT;
	delay_ms(400);
}

unsigned short calcheckcode(unsigned char *dat){
	int n,m;
	unsigned short chk = 0xFFFF;
	unsigned short polymail = 0xA001;

	for (n = 0; n < sizeof(device_comm_frame) - 2; n++) {
		chk ^= ((unsigned short) dat[n] & 0x00FF);
		for (m = 0; m < 8; m++) {
			if ((chk &0x0001) != 0) {
				chk >>= 1;
				chk ^= polymail;
			}
			else
				chk >>= 1;
		}
	}
	return chk;
}
 bool check(unsigned char *dat){
	int n;
	unsigned short crc = calcheckcode(dat);
	unsigned short chk = ((unsigned short) (dat[sizeof(device_comm_frame)-2]) & 0xFF);
	chk += ((unsigned short) (dat[sizeof(device_comm_frame)-1] << 8) & 0xFF);
	
	if (crc == chk) return true;
	return false;
}

int process_rcv_data()
{
	int datalen;
	vs8 ret = -1;
	receive_IOT *prcv_buf = (receive_IOT *)rcv_buf_from_server;
	datalen = UART_receive_buf(rcv_buf_from_server);
	buff_pos = 0;
	if (((rcv_buf_from_server[0] != 0xAB) ||
			(rcv_buf_from_server[1] != 0xAB) ||
			(rcv_buf_from_server[2] != 0xAB) ||
			(rcv_buf_from_server[3] != 0xAB))){
		ret = -1;
		LED_RGB_Control(0,0,5);
		delay_ms(1000);
		goto done;
	}
	
	if (check(rcv_buf_from_server)) { // check error
		ret = -1;
		goto done;
	}
	// data ok
	memcpy(sensor_data, (u8 *)&prcv_buf->sensor_dev1, sizeof(receive_sensor_data) * (prcv_buf->sensor_num));
	ret = 0;
done:
	return ret;
}

void Hal_Init(void)
{
	UARTx_Init();
	RGB_LED_Init();
	DHT11_Init();
	IR_Init();
	Motor_Init();
}

int main(void)
{
	int i;
	int count = 1;
	int R,G,B;
	int motor_velocity;
	uint8_t chTemper;
	uint8_t chHumidity;
	device_comm_frame tcpip_data;
	
	R=G=B=5;
	motor_velocity=0;
	system_init();
	Hal_Init();

	setup_wifi();
	
	tcpip_data.START_F0 = (STARTFLAG >> 24) & 0xff;
	tcpip_data.START_F1 = (STARTFLAG >> 16) & 0xff;
	tcpip_data.START_F2 = (STARTFLAG >> 8) & 0xff;
	tcpip_data.START_F3 = (STARTFLAG >> 0) & 0xff;
	
	tcpip_data.num1 = (NUM >> 24) & 0xff;
	tcpip_data.num2 = (NUM >> 16) & 0xff;
	tcpip_data.num3 = (NUM >> 8) & 0xff;
	tcpip_data.num4 = (NUM >> 0) & 0xff;
	
	while (count++) {
		delay_ms(1000);
		
		if (process_rcv_data() == 0){   //OK    
						R = sensor_data[0].V1;
						G = sensor_data[0].V2;
						B = sensor_data[0].V3;
						LED_RGB_Control(R, G, B);
						motor_velocity= sensor_data[2].V1;
						Motor_status(motor_velocity);
		}
		else{   //ERROR
						;
		}
		tcpip_data.heartbeat = 1;
		tcpip_data.sensor_num = 3;

		tcpip_data.sensor_dev1.ID = 1;
		tcpip_data.sensor_dev1.V1 = R;
		tcpip_data.sensor_dev1.V2 = G;
		tcpip_data.sensor_dev1.V3 = B;
		
		DHT11_Read_Data(&chTemper,&chHumidity);
		tcpip_data.sensor_dev2.ID = 2;
		tcpip_data.sensor_dev2.V1 = chTemper;
		tcpip_data.sensor_dev2.V2 = chHumidity;
		tcpip_data.sensor_dev2.V3 = 0;
		
		IR_Handle();
		tcpip_data.sensor_dev3.ID = 3;
		tcpip_data.sensor_dev3.V1 = 32;
		tcpip_data.sensor_dev3.V2 = 0;
		tcpip_data.sensor_dev3.V3 = 0;

		tcpip_data.chkorl = calcheckcode((unsigned char *) &tcpip_data);
		SendData((uint8_t*)&tcpip_data,sizeof(device_comm_frame));
		delay_ms(1000);
	}
}


/*-------------------------------END OF FILE-------------------------------*/
