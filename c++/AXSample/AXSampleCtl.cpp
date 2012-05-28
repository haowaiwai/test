// AXSampleCtl.cpp : CAXSampleCtl ��ʵ��

#include "stdafx.h"
#include "AXSampleCtl.h"

// CAXSampleCtl


STDMETHODIMP CAXSampleCtl::get(void)
{
	// TODO: �ڴ����ʵ�ִ���
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::get_PicFile(BSTR* pVal)
{
	// TODO: �ڴ����ʵ�ִ���
	//int iIndex = rand()%m_filelist.size();
	//printf("index %d(%d)\n",iIndex,m_filelist.size());
	//CComBSTR path(m_filelist.at(iIndex).c_str());
	//*pVal=path.Detach();
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_PicFile(BSTR newVal)
{
	// TODO: �ڴ����ʵ�ִ���

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::get_PicPath(BSTR ProgramPath, BSTR* pVal)
{
	// TODO: �ڴ����ʵ�ִ���

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_PicPath(BSTR ProgramPath, BSTR newVal)
{
	// TODO: �ڴ����ʵ�ִ���

	return S_OK;
}

STDMETHODIMP CAXSampleCtl::GetPicPath(BSTR ProgramPath, BSTR* PicPath)
{
	// TODO: �ڴ����ʵ�ִ���
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::SetPicPath(BSTR PicPath)
{
	// TODO: �ڴ����ʵ�ִ���
	m_PicPath = PicPath;
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::SearchFile(void)
{
	// TODO: �ڴ����ʵ�ִ���
	SearchFiles();
	InvokeFunc();
	return S_OK;
}

DWORD CAXSampleCtl::MonitorFile(LPVOID pParam)
{
	CAXSampleCtl *pMonitor = (CAXSampleCtl*)pParam;
	//	��ʼ��ɨ��һ��
	/*printf("first search begin\n");
	pMonitor->SearchFiles();
	printf("first search end\n");
	printf("first Invoke start\n");*/
	//	����
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
			//	�ļ��仯
			FindCloseChangeNotification(hNotify);
			CloseHandle(hNotify);
			printf("check end search begin\n");
			pMonitor->SearchFiles();
			printf("search end\n");
			printf("Invoke start\n");
			//	����
			pMonitor->InvokeFunc();
			printf("Invoke end\n");
		}
		else if(dwRet == WAIT_OBJECT_0 + 1)
		{
			//	�˳�
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
	// TODO: �ڴ����ʵ�ִ���
	ResumeThread(m_hMonitorThread);
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::test(VARIANT* ll)
{
	// TODO: �ڴ����ʵ�ִ���
	
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::GetFileList(VARIANT* pList)
{
	// TODO: �ڴ����ʵ�ִ���
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
	// TODO: �ڴ����ʵ�ִ���
	*pVal = m_onState;
	return S_OK;
}

STDMETHODIMP CAXSampleCtl::put_OnChange(IDispatch* newVal)
{
	// TODO: �ڴ����ʵ�ִ���
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
	if (m_onState)        // ���������¼�
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