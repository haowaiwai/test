// TestCtl.h : CTestCtl ������

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
#include "_ITestCtlEvents_CP.h"
#include <comdef.h>


#if defined(_WIN32_WCE) && !defined(_CE_DCOM) && !defined(_CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA)
#error "Windows CE ƽ̨(�粻�ṩ��ȫ DCOM ֧�ֵ� Windows Mobile ƽ̨)���޷���ȷ֧�ֵ��߳� COM ���󡣶��� _CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA ��ǿ�� ATL ֧�ִ������߳� COM ����ʵ�ֲ�����ʹ���䵥�߳� COM ����ʵ�֡�rgs �ļ��е��߳�ģ���ѱ�����Ϊ��Free����ԭ���Ǹ�ģ���Ƿ� DCOM Windows CE ƽ̨֧�ֵ�Ψһ�߳�ģ�͡�"
#endif



// CTestCtl

class ATL_NO_VTABLE CTestCtl :
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CTestCtl, &CLSID_TestCtl>,
	public ISupportErrorInfo,
	public IConnectionPointContainerImpl<CTestCtl>,
	public CProxy_ITestCtlEvents<CTestCtl>,
	public IObjectWithSiteImpl<CTestCtl>,
	public IDispatchImpl<ITestCtl, &IID_ITestCtl, &LIBID_AXSampleLib, /*wMajor =*/ 1, /*wMinor =*/ 0>,
	public IObjectSafetyImpl<CTestCtl, INTERFACESAFE_FOR_UNTRUSTED_CALLER | INTERFACESAFE_FOR_UNTRUSTED_DATA>
{
public:
	CTestCtl()
	{
	}

#ifndef _CE_DCOM
DECLARE_REGISTRY_RESOURCEID(IDR_TESTCTL)
#endif


BEGIN_COM_MAP(CTestCtl)
	COM_INTERFACE_ENTRY(ITestCtl)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(ISupportErrorInfo)
	COM_INTERFACE_ENTRY(IConnectionPointContainer)
	COM_INTERFACE_ENTRY(IObjectWithSite)
	COM_INTERFACE_ENTRY(IObjectSafety)
END_COM_MAP()

BEGIN_CONNECTION_POINT_MAP(CTestCtl)
	CONNECTION_POINT_ENTRY(__uuidof(_ITestCtlEvents))
END_CONNECTION_POINT_MAP()
// ISupportsErrorInfo
	STDMETHOD(InterfaceSupportsErrorInfo)(REFIID riid);


	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		return S_OK;
	}

	void FinalRelease()
	{
	}

public:

	STDMETHOD(get_OnState)(IDispatch** pVal);
	STDMETHOD(put_OnState)(IDispatch* newVal);
	STDMETHOD(test)(void);
	IDispatchPtr m_onState;        // �¼�����
};

OBJECT_ENTRY_AUTO(__uuidof(TestCtl), CTestCtl)
