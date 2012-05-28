#include "stdafx.h"
#include "ping.h"

void main()
{
  USES_CONVERSION;

	if (__argc != 2)
	{
		printf("Usage: CPing HostName | IPAddress\n");
		return;
	}

#ifdef CPING_USE_ICMP
{
	CPing p1;
	CPingReply pr1;
	if (p1.Ping1(A2T(__argv[1]), pr1))
	{
		hostent* phostent = gethostbyaddr((char *)&pr1.Address.S_un.S_addr, 4, PF_INET);
		printf("%d.%d.%d.%d [%s], replied in RTT:%dms\n", 
					 pr1.Address.S_un.S_un_b.s_b1, pr1.Address.S_un.S_un_b.s_b2, pr1.Address.S_un.S_un_b.s_b3, 
					 pr1.Address.S_un.S_un_b.s_b4, phostent->h_name, pr1.RTT);
	}
	else
	  printf("Failed in call to ping, GetLastError returns: %d", GetLastError());
}
#endif

#ifdef CPING_USE_WINSOCK2
{
	for(int i=0;i<4;i++)
	{
		CPing p2;
		CPingReply pr2;
		if (p2.Ping2(A2T(__argv[1]), pr2))
		{
			hostent* phostent = gethostbyaddr((char *)&pr2.Address.S_un.S_addr, 4, PF_INET);
			if(phostent == NULL)
				printf("%d.%d.%d.%d, replied in RTT:%dms\n", 
				pr2.Address.S_un.S_un_b.s_b1, pr2.Address.S_un.S_un_b.s_b2, pr2.Address.S_un.S_un_b.s_b3, 
				pr2.Address.S_un.S_un_b.s_b4, pr2.RTT);
			else
				printf("%d.%d.%d.%d [%s], replied in RTT:%dms\n", 
				pr2.Address.S_un.S_un_b.s_b1, pr2.Address.S_un.S_un_b.s_b2, pr2.Address.S_un.S_un_b.s_b3, 
				pr2.Address.S_un.S_un_b.s_b4, phostent->h_name, pr2.RTT);
		}
		else
			printf("Failed in call to ping, GetLastError returns: %d", GetLastError());
	}
	}	
#endif
}
