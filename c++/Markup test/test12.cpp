// test12.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"


#include <boost/crc.hpp>      // for boost::crc_basic, boost::crc_optimal
#include <boost/cstdint.hpp>  // for boost::uint16_t

#include <algorithm>  // for std::for_each
#include <cassert>    // for assert
#include <cstddef>    // for std::size_t
#include <iostream>   // for std::cout
#include <ostream>    // for std::endl
#include <bitset>
#include "Markup.h"

using namespace std;


// Main function
int main ()
{
	//boost::crc_32_type result;
	//计算一个字符的CRC值
	//result.process_byte('a');
	//std::cout << std::hex << std::uppercase << result.checksum() << std::endl;
	/*unsigned char b[] = {0xc4,0x06,0x3c,0xff};
	unsigned char t = b[0]^b[1];
	for(int i=2;i<sizeof(b);i++)
	{
		t = t^b[i];
	}*/
	CFile file;
	CFileException fe;
	if(file.Open(_T("c:\\UserInfo.xml"),CFile::modeRead,&fe))
	{
		return -1;
	}
	file.Close();
	CMarkup xml;
	xml.SetDoc("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
	xml.AddElem( L"ORDER" );
	xml.AddChildElem( L"ITEM" );
	xml.IntoElem();
	xml.AddChildElem( L"SN", L"132487A-J" );
	xml.AddChildElem( L"NAME", L"哈哈哈" );
	xml.AddChildElem( L"QTY", L"1" );
	xml.Save(L"c:\\UserInfo.xml");
	/*CMarkup xml;
	xml.Load(L"c:\\UserInfo.xml");
	while ( xml.FindChildElem(L"ITEM") )
	{
		xml.IntoElem();
		xml.FindChildElem( L"SN" );
		CString csSN = xml.GetChildData();
		xml.FindChildElem( L"NAME" );
		CString name = xml.GetChildData();
		xml.FindChildElem( L"QTY" );
		int nQty = _tstoi( xml.GetChildData() );
		xml.OutOfElem();
	}*/
	bitset<4> bit(1);
	bit.set(3,1);
	cout<<bit.to_ulong()<<endl; 
	unsigned char a = 10;
	int i = 1030;
	std::cout<<i<<std::endl;
	std::cin>>i;
	return 0;
}
