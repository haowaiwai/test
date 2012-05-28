// AXSampleCtl.cpp : CAXSampleCtl 的实现

#include "stdafx.h"
#include "AXSampleCtl.h"

// CAXSampleCtl


STDMETHODIMP CAXSampleCtl::get(void)
{
	// TODO: 在此添加实现代码
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::get_PicFile(BSTR* pVal)
{
	// TODO: 在此添加实现代码
	//int iIndex = rand()%m_filelist.size();
	//printf("index %d(%d)\n",iIndex,m_filelist.size());
	//CComBSTR path(m_filelist.at(iIndex).c_str());
	//*pVal=path.Detach();
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_PicFile(BSTR newVal)
{
	// TODO: 在此添加实现代码

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::get_PicPath(BSTR ProgramPath, BSTR* pVal)
{
	// TODO: 在此添加实现代码

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_PicPath(BSTR ProgramPath, BSTR newVal)
{
	// TODO: 在此添加实现代码

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::GetPicPath(BSTR ProgramPath, BSTR* PicPath)
{
	// TODO: 在此添加实现代码
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::SetPicPath(BSTR PicPath)
{
	// TODO: 在此添加实现代码
	m_PicPath = PicPath;
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::SearchFile(void)
{
	// TODO: 在此添加实现代码
	SearchFiles();
	InvokeFunc();
	return S_OK;
}

DWORD CAXSampleCtl::MonitorFile(LPVOID pParam)
{
	CAXSampleCtl *pMonitor = (CAXSampleCtl*)pParam;
	//	初始化扫描一次
	/*printf("first search begin\n");
	pMonitor->SearchFiles();
	printf("first search end\n");
	printf("first Invoke start\n");*/
	//	调用
	//pMonitor->InvokeFunc();
	printf("first Invoke end\n");
	while(1)
	{
		HANDLE hNotify = FindFirstChangeNotification(pMonitor->m_PicPath,TRUE,FILE_NOTIFY_CHANGE_FILE_NAME);
		HANDLE hHandles[] = {hNotify,pMonitor->m_hEventExit};
		printf("check begin\n");
		DWORD dwRet = WaitForMultipleObjects(2,hHandles,FALSE,INFINITE);
		if(dwRet == WAIT_OBJECT_0)
		{
			//	文件变化
			FindCloseChangeNotification(hNotify);
			CloseHandle(hNotify);
			printf("check end search begin\n");
			pMonitor->SearchFiles();
			printf("search end\n");
			printf("Invoke start\n");
			//	调用
			pMonitor->InvokeFunc();
			printf("Invoke end\n");
		}
		else if(dwRet == WAIT_OBJECT_0 + 1)
		{
			//	退出
			FindCloseChangeNotification(hNotify);
			CloseHandle(hNotify);
			printf("event exit\n");
			break;
		}
		else
		{
			FindCloseChangeNotification(hNotify);
			CloseHandle(hNotify);
			printf("Wait For Code:%d\n",dwRet);
			Sleep(500);
		}
	}
	printf("monitor exit\n");
	return 0;
}

void CAXSampleCtl::SearchFiles()
{
	m_filelist.clear();
	CComBSTR bstrPath(m_PicPath);
	bstrPath.Append("*.*");
	WIN32_FIND_DATA data;
	HANDLE hSearch=FindFirstFile(bstrPath,&data);
	if(hSearch != INVALID_HANDLE_VALUE)  
	{ 
		do
		{
			if( _tcscmp(data.cFileName,L".") == 0)
				continue;
			if( _tcscmp(data.cFileName,L"..") == 0)
				continue;
			if( data.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)
				continue;

			m_filelist.push_back(data.cFileName);
		}
		while(FindNextFile(hSearch,&data));
		FindClose(hSearch);
	}
}
STDMETHODIMP CAXSampleCtl::Start(void)
{
	// TODO: 在此添加实现代码
	ResumeThread(m_hMonitorThread);
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::test(VARIANT* ll)
{
	// TODO: 在此添加实现代码
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::GetFileList(VARIANT* pList)
{
	// TODO: 在此添加实现代码
	CComSafeArray<VARIANT> Buff;
	for(vector<wstring>::size_type i=0;i<m_filelist.size();i++)
	{
		Buff.Add(CComVariant(m_filelist.at(i).c_str()));
	}
	CComVariant(Buff).Detach(pList); 
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::get_OnChange(IDispatch** pVal)
{
	// TODO: 在此添加实现代码
	*pVal = m_onState;
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_OnChange(IDispatch* newVal)
{
	// TODO: 在此添加实现代码
	m_onState = newVal;
	return S_OK;
}

void CAXSampleCtl::InvokeFunc()
{
	CComVariant result;
	CComSafeArray<VARIANT> Buff;
	/*for(vector<wstring>::size_type i=0;i<m_filelist.size();i++)
	{
		Buff.Add(CComVariant(m_filelist.at(i).c_str()));
	}*/
	Buff.Add(CComVariant("b"));
	Buff.Add(CComVariant("a"));
	CComVariant avarParams[1] = {Buff};
	DISPPARAMS dispParams = {avarParams, NULL, 1, 0};
	EXCEPINFO excepInfo;
	memset(&excepInfo, 0, sizeof excepInfo);
	UINT nArgErr = (UINT)-1;      // initialize to invalid arg
	if (m_onState)        // 激发属性事件
	{
		HRESULT hr = m_onState->Invoke(0, IID_NULL, LOCALE_USER_DEFAULT,
		DISPATCH_METHOD, &dispParams, &result, &excepInfo, &nArgErr);
		if(hr == DISP_E_BADPARAMCOUNT)
		{

		}
		else if(hr == DISP_E_BADVARTYPE)
		{

		}
		else if(hr == DISP_E_EXCEPTION)
		{

		}
		else if(hr == DISP_E_MEMBERNOTFOUND)
		{

		}
		else if(hr == DISP_E_NONAMEDARGS)
		{

		}
		else if(hr == DISP_E_OVERFLOW)
		{

		}
		else if(hr == DISP_E_PARAMNOTFOUND)
		{

		}
		else if(hr == DISP_E_TYPEMISMATCH)
		{

		}
		else if(hr == DISP_E_UNKNOWNINTERFACE)
		{

		}
		else if(hr == DISP_E_UNKNOWNLCID)
		{
		}
		else if(hr == DISP_E_PARAMNOTOPTIONAL)
		{

		}
		
		if(!SUCCEEDED(hr))
		{
			LPTSTR *pMsgBuf = NULL;
			::FormatMessage(
				FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM,
				NULL,
				hr,  
				MAKELANGID(LANG_NEUTRAL,SUBLANG_DEFAULT),
				(LPTSTR)&pMsgBuf,
				0,
				NULL);
			MessageBox(NULL,(LPCTSTR)pMsgBuf,(LPCTSTR)pMsgBuf,MB_OK);
		}
		else
		{
			MessageBox(NULL,L"OK",L"OK",MB_OK);
		}
	}
}