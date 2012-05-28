// TestCtl.cpp : CTestCtl 的实现

#include "stdafx.h"
#include "TestCtl.h"


// CTestCtl

STDMETHODIMP CTestCtl::InterfaceSupportsErrorInfo(REFIID riid)
{
	static const IID* arr[] = 
	{
		&IID_ITestCtl
	};

	for (int i=0; i < sizeof(arr) / sizeof(arr[0]); i++)
	{
		if (InlineIsEqualGUID(*arr[i],riid))
			return S_OK;
	}
	return S_FALSE;
}

STDMETHODIMP CTestCtl::get_OnState(IDispatch** pVal)
{
	// TODO: 在此添加实现代码
	*pVal = m_onState;
	return S_OK;
}

STDMETHODIMP CTestCtl::put_OnState(IDispatch* newVal)
{
	// TODO: 在此添加实现代码
	m_onState = newVal;
	return S_OK;
}

STDMETHODIMP CTestCtl::test(void)
{
	// TODO: 在此添加实现代码
	CComVariant result;
	CComSafeArray<VARIANT> Buff;
	Buff.Add(CComVariant("b"));
	Buff.Add(CComVariant("a"));
	CComVariant avarParams[1] = {Buff};
	DISPPARAMS dispParams = {avarParams, NULL, 1, 0};
	EXCEPINFO excepInfo;
	memset(&excepInfo, 0, sizeof excepInfo);
	UINT nArgErr = (UINT)-1;      // initialize to invalid arg
	if (m_onState)        // 激发属性事件
		HRESULT hr = m_onState->Invoke(0, IID_NULL, LOCALE_USER_DEFAULT,
		DISPATCH_METHOD, &dispParams, &result, &excepInfo, &nArgErr);
	return S_OK;
}
