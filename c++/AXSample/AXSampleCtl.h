// AXSampleCtl.h : CAXSampleCtl ������

#pragma once
#ifdef STANDARDSHELL_UI_MODEL
#include "resource.h"
#endif
#ifdef POCKETPC2003_UI_MODEL
#include "resourceppc.h"
#endif
#ifdef SMARTPHONE2003_UI_MODEL
#include "resourcesp.h"
#endif
#ifdef AYGSHELL_UI_MODEL
#include "resourceayg.h"
#endif

#include "AXSample.h"
#include <comdef.h>
#include <vector>
#include <string>
using std::vector;
using std::wstring;


#if defined(_WIN32_WCE) && !defined(_CE_DCOM) && !defined(_CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA)
#error "Windows CE ƽ̨(�粻�ṩ��ȫ DCOM ֧�ֵ� Windows Mobile ƽ̨)���޷���ȷ֧�ֵ��߳� COM ���󡣶��� _CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA ��ǿ�� ATL ֧�ִ������߳� COM ����ʵ�ֲ�����ʹ���䵥�߳� COM ����ʵ�֡�rgs �ļ��е��߳�ģ���ѱ�����Ϊ��Free����ԭ���Ǹ�ģ���Ƿ� DCOM Windows CE ƽ̨֧�ֵ�Ψһ�߳�ģ�͡�"
#endif



// CAXSampleCtl

class ATL_NO_VTABLE CAXSampleCtl :
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CAXSampleCtl, &CLSID_AXSampleCtl>,
	public IDispatchImpl<IAXSampleCtl, &IID_IAXSampleCtl, &LIBID_AXSampleLib, /*wMajor =*/ 1, /*wMinor =*/ 0>,
	public IObjectSafetyImpl<CAXSampleCtl, INTERFACESAFE_FOR_UNTRUSTED_CALLER | INTERFACESAFE_FOR_UNTRUSTED_DATA>
{
public:
	CAXSampleCtl()
	{
		srand(GetTickCount());
		m_hMonitorThread = NULL;
	}

#ifndef _CE_DCOM
DECLARE_REGISTRY_RESOURCEID(IDR_AXSAMPLECTL)
#endif


BEGIN_COM_MAP(CAXSampleCtl)
	COM_INTERFACE_ENTRY(IAXSampleCtl)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(IObjectSafety)
END_COM_MAP()



	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		printf("FinalConstruct begin\n");
		m_hEventExit = CreateEvent(NULL,TRUE,FALSE,NULL);
		m_hMonitorThread = CreateThread(
			NULL,
			NULL,
			MonitorFile,
			(LPVOID)this,
			CREATE_SUSPENDED,
			NULL);
		printf("FinalConstruct exit\n");
		return S_OK;
	}

	void FinalRelease()
	{
		printf("FinalRelease begin\n");
		SetEvent(m_hEventExit);
		Sleep(0);
		if(m_hMonitorThread != NULL)
		{
			WaitForSingleObject(m_hMonitorThread,INFINITE);
			CloseHandle(m_hMonitorThread);
		}
		CloseHandle(m_hEventExit);
		printf("FinalRelease exit\n");
	}

public:

	STDMETHOD(get)(void);
	STDMETHOD(get_PicFile)(BSTR* pVal);
	STDMETHOD(put_PicFile)(BSTR newVal);
	STDMETHOD(get_PicPath)(BSTR ProgramPath, BSTR* pVal);
	STDMETHOD(put_PicPath)(BSTR ProgramPath, BSTR newVal);
	STDMETHOD(GetPicPath)(BSTR ProgramPath, BSTR* PicPath);
	STDMETHOD(SetPicPath)(BSTR PicPath);
	STDMETHOD(SearchFile)(void);
	vector<wstring> m_filelist;
	CComBSTR m_PicPath;
	HANDLE m_hEventExit;
	HANDLE m_hMonitorThread;
	static  DWORD	WINAPI	MonitorFile(LPVOID pParam);
	void SearchFiles();
	void InvokeFunc();
	STDMETHOD(Start)(void);
	STDMETHOD(test)(VARIANT* ll);
	STDMETHOD(GetFileList)(VARIANT* pList);
	STDMETHOD(get_OnChange)(IDispatch** pVal);
	STDMETHOD(put_OnChange)(IDispatch* newVal);
	IDispatchPtr m_onState;
};

OBJECT_ENTRY_AUTO(__uuidof(AXSampleCtl), CAXSampleCtl)
