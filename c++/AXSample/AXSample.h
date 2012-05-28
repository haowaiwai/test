/* this ALWAYS GENERATED file contains the definitions for the interfaces */


/* File created by MIDL compiler version 5.01.0164 */
/* at Thu Jun 17 16:15:09 2010
 */
/* Compiler settings for .\AXSample.idl:
    Oicf (OptLev=i2), W1, Zp8, env=Win32, ms_ext, c_ext
    error checks: allocation ref bounds_check enum stub_data 
*/
//@@MIDL_FILE_HEADING(  )


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 440
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__

#ifndef COM_NO_WINDOWS_H
#include "windows.h"
#include "ole2.h"
#endif /*COM_NO_WINDOWS_H*/

#ifndef __AXSample_h__
#define __AXSample_h__

#ifdef __cplusplus
extern "C"{
#endif 

/* Forward Declarations */ 

#ifndef __IAXSampleCtl_FWD_DEFINED__
#define __IAXSampleCtl_FWD_DEFINED__
typedef interface IAXSampleCtl IAXSampleCtl;
#endif 	/* __IAXSampleCtl_FWD_DEFINED__ */


#ifndef __ITestCtl_FWD_DEFINED__
#define __ITestCtl_FWD_DEFINED__
typedef interface ITestCtl ITestCtl;
#endif 	/* __ITestCtl_FWD_DEFINED__ */


#ifndef __AXSampleCtl_FWD_DEFINED__
#define __AXSampleCtl_FWD_DEFINED__

#ifdef __cplusplus
typedef class AXSampleCtl AXSampleCtl;
#else
typedef struct AXSampleCtl AXSampleCtl;
#endif /* __cplusplus */

#endif 	/* __AXSampleCtl_FWD_DEFINED__ */


#ifndef ___ITestCtlEvents_FWD_DEFINED__
#define ___ITestCtlEvents_FWD_DEFINED__
typedef interface _ITestCtlEvents _ITestCtlEvents;
#endif 	/* ___ITestCtlEvents_FWD_DEFINED__ */


#ifndef __TestCtl_FWD_DEFINED__
#define __TestCtl_FWD_DEFINED__

#ifdef __cplusplus
typedef class TestCtl TestCtl;
#else
typedef struct TestCtl TestCtl;
#endif /* __cplusplus */

#endif 	/* __TestCtl_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"
#include "atliface.h"

void __RPC_FAR * __RPC_USER MIDL_user_allocate(size_t);
void __RPC_USER MIDL_user_free( void __RPC_FAR * ); 

#ifndef __IAXSampleCtl_INTERFACE_DEFINED__
#define __IAXSampleCtl_INTERFACE_DEFINED__

/* interface IAXSampleCtl */
/* [unique][helpstring][nonextensible][dual][uuid][object] */ 


EXTERN_C const IID IID_IAXSampleCtl;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("1E03BF60-02DB-4587-B793-C4EE48279AA9")
    IAXSampleCtl : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE get( void) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_PicFile( 
            /* [retval][out] */ BSTR __RPC_FAR *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_PicFile( 
            /* [in] */ BSTR newVal) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_PicPath( 
            /* [in] */ BSTR ProgramPath,
            /* [retval][out] */ BSTR __RPC_FAR *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_PicPath( 
            /* [in] */ BSTR ProgramPath,
            /* [in] */ BSTR newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetPicPath( 
            /* [in] */ BSTR ProgramPath,
            /* [out] */ BSTR __RPC_FAR *PicPath) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SetPicPath( 
            /* [in] */ BSTR PicPath) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE SearchFile( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE Start( void) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE test( 
            /* [out] */ VARIANT __RPC_FAR *ll) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE GetFileList( 
            /* [retval][out] */ VARIANT __RPC_FAR *pList) = 0;
        
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_OnChange( 
            /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_OnChange( 
            /* [in] */ IDispatch __RPC_FAR *newVal) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IAXSampleCtlVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *QueryInterface )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void __RPC_FAR *__RPC_FAR *ppvObject);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *AddRef )( 
            IAXSampleCtl __RPC_FAR * This);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *Release )( 
            IAXSampleCtl __RPC_FAR * This);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfoCount )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [out] */ UINT __RPC_FAR *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfo )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo __RPC_FAR *__RPC_FAR *ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetIDsOfNames )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR __RPC_FAR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID __RPC_FAR *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *Invoke )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS __RPC_FAR *pDispParams,
            /* [out] */ VARIANT __RPC_FAR *pVarResult,
            /* [out] */ EXCEPINFO __RPC_FAR *pExcepInfo,
            /* [out] */ UINT __RPC_FAR *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *get )( 
            IAXSampleCtl __RPC_FAR * This);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *get_PicFile )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [retval][out] */ BSTR __RPC_FAR *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *put_PicFile )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ BSTR newVal);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *get_PicPath )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ BSTR ProgramPath,
            /* [retval][out] */ BSTR __RPC_FAR *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *put_PicPath )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ BSTR ProgramPath,
            /* [in] */ BSTR newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetPicPath )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ BSTR ProgramPath,
            /* [out] */ BSTR __RPC_FAR *PicPath);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *SetPicPath )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ BSTR PicPath);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *SearchFile )( 
            IAXSampleCtl __RPC_FAR * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *Start )( 
            IAXSampleCtl __RPC_FAR * This);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *test )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [out] */ VARIANT __RPC_FAR *ll);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetFileList )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [retval][out] */ VARIANT __RPC_FAR *pList);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *get_OnChange )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *put_OnChange )( 
            IAXSampleCtl __RPC_FAR * This,
            /* [in] */ IDispatch __RPC_FAR *newVal);
        
        END_INTERFACE
    } IAXSampleCtlVtbl;

    interface IAXSampleCtl
    {
        CONST_VTBL struct IAXSampleCtlVtbl __RPC_FAR *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IAXSampleCtl_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define IAXSampleCtl_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define IAXSampleCtl_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define IAXSampleCtl_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define IAXSampleCtl_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define IAXSampleCtl_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define IAXSampleCtl_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define IAXSampleCtl_get(This)	\
    (This)->lpVtbl -> get(This)

#define IAXSampleCtl_get_PicFile(This,pVal)	\
    (This)->lpVtbl -> get_PicFile(This,pVal)

#define IAXSampleCtl_put_PicFile(This,newVal)	\
    (This)->lpVtbl -> put_PicFile(This,newVal)

#define IAXSampleCtl_get_PicPath(This,ProgramPath,pVal)	\
    (This)->lpVtbl -> get_PicPath(This,ProgramPath,pVal)

#define IAXSampleCtl_put_PicPath(This,ProgramPath,newVal)	\
    (This)->lpVtbl -> put_PicPath(This,ProgramPath,newVal)

#define IAXSampleCtl_GetPicPath(This,ProgramPath,PicPath)	\
    (This)->lpVtbl -> GetPicPath(This,ProgramPath,PicPath)

#define IAXSampleCtl_SetPicPath(This,PicPath)	\
    (This)->lpVtbl -> SetPicPath(This,PicPath)

#define IAXSampleCtl_SearchFile(This)	\
    (This)->lpVtbl -> SearchFile(This)

#define IAXSampleCtl_Start(This)	\
    (This)->lpVtbl -> Start(This)

#define IAXSampleCtl_test(This,ll)	\
    (This)->lpVtbl -> test(This,ll)

#define IAXSampleCtl_GetFileList(This,pList)	\
    (This)->lpVtbl -> GetFileList(This,pList)

#define IAXSampleCtl_get_OnChange(This,pVal)	\
    (This)->lpVtbl -> get_OnChange(This,pVal)

#define IAXSampleCtl_put_OnChange(This,newVal)	\
    (This)->lpVtbl -> put_OnChange(This,newVal)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_get_Proxy( 
    IAXSampleCtl __RPC_FAR * This);


void __RPC_STUB IAXSampleCtl_get_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_get_PicFile_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [retval][out] */ BSTR __RPC_FAR *pVal);


void __RPC_STUB IAXSampleCtl_get_PicFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_put_PicFile_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ BSTR newVal);


void __RPC_STUB IAXSampleCtl_put_PicFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_get_PicPath_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ BSTR ProgramPath,
    /* [retval][out] */ BSTR __RPC_FAR *pVal);


void __RPC_STUB IAXSampleCtl_get_PicPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_put_PicPath_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ BSTR ProgramPath,
    /* [in] */ BSTR newVal);


void __RPC_STUB IAXSampleCtl_put_PicPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_GetPicPath_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ BSTR ProgramPath,
    /* [out] */ BSTR __RPC_FAR *PicPath);


void __RPC_STUB IAXSampleCtl_GetPicPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_SetPicPath_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ BSTR PicPath);


void __RPC_STUB IAXSampleCtl_SetPicPath_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_SearchFile_Proxy( 
    IAXSampleCtl __RPC_FAR * This);


void __RPC_STUB IAXSampleCtl_SearchFile_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_Start_Proxy( 
    IAXSampleCtl __RPC_FAR * This);


void __RPC_STUB IAXSampleCtl_Start_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_test_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [out] */ VARIANT __RPC_FAR *ll);


void __RPC_STUB IAXSampleCtl_test_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_GetFileList_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [retval][out] */ VARIANT __RPC_FAR *pList);


void __RPC_STUB IAXSampleCtl_GetFileList_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_get_OnChange_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal);


void __RPC_STUB IAXSampleCtl_get_OnChange_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE IAXSampleCtl_put_OnChange_Proxy( 
    IAXSampleCtl __RPC_FAR * This,
    /* [in] */ IDispatch __RPC_FAR *newVal);


void __RPC_STUB IAXSampleCtl_put_OnChange_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __IAXSampleCtl_INTERFACE_DEFINED__ */


#ifndef __ITestCtl_INTERFACE_DEFINED__
#define __ITestCtl_INTERFACE_DEFINED__

/* interface ITestCtl */
/* [unique][helpstring][nonextensible][dual][uuid][object] */ 


EXTERN_C const IID IID_ITestCtl;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("B68D04E3-2F7B-4565-85A9-D93B7AFE34EC")
    ITestCtl : public IDispatch
    {
    public:
        virtual /* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE get_OnState( 
            /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal) = 0;
        
        virtual /* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE put_OnState( 
            /* [in] */ IDispatch __RPC_FAR *newVal) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE test( void) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct ITestCtlVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *QueryInterface )( 
            ITestCtl __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void __RPC_FAR *__RPC_FAR *ppvObject);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *AddRef )( 
            ITestCtl __RPC_FAR * This);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *Release )( 
            ITestCtl __RPC_FAR * This);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfoCount )( 
            ITestCtl __RPC_FAR * This,
            /* [out] */ UINT __RPC_FAR *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfo )( 
            ITestCtl __RPC_FAR * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo __RPC_FAR *__RPC_FAR *ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetIDsOfNames )( 
            ITestCtl __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR __RPC_FAR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID __RPC_FAR *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *Invoke )( 
            ITestCtl __RPC_FAR * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS __RPC_FAR *pDispParams,
            /* [out] */ VARIANT __RPC_FAR *pVarResult,
            /* [out] */ EXCEPINFO __RPC_FAR *pExcepInfo,
            /* [out] */ UINT __RPC_FAR *puArgErr);
        
        /* [helpstring][id][propget] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *get_OnState )( 
            ITestCtl __RPC_FAR * This,
            /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal);
        
        /* [helpstring][id][propput] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *put_OnState )( 
            ITestCtl __RPC_FAR * This,
            /* [in] */ IDispatch __RPC_FAR *newVal);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *test )( 
            ITestCtl __RPC_FAR * This);
        
        END_INTERFACE
    } ITestCtlVtbl;

    interface ITestCtl
    {
        CONST_VTBL struct ITestCtlVtbl __RPC_FAR *lpVtbl;
    };

    

#ifdef COBJMACROS


#define ITestCtl_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define ITestCtl_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define ITestCtl_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define ITestCtl_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define ITestCtl_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define ITestCtl_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define ITestCtl_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)


#define ITestCtl_get_OnState(This,pVal)	\
    (This)->lpVtbl -> get_OnState(This,pVal)

#define ITestCtl_put_OnState(This,newVal)	\
    (This)->lpVtbl -> put_OnState(This,newVal)

#define ITestCtl_test(This)	\
    (This)->lpVtbl -> test(This)

#endif /* COBJMACROS */


#endif 	/* C style interface */



/* [helpstring][id][propget] */ HRESULT STDMETHODCALLTYPE ITestCtl_get_OnState_Proxy( 
    ITestCtl __RPC_FAR * This,
    /* [retval][out] */ IDispatch __RPC_FAR *__RPC_FAR *pVal);


void __RPC_STUB ITestCtl_get_OnState_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id][propput] */ HRESULT STDMETHODCALLTYPE ITestCtl_put_OnState_Proxy( 
    ITestCtl __RPC_FAR * This,
    /* [in] */ IDispatch __RPC_FAR *newVal);


void __RPC_STUB ITestCtl_put_OnState_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);


/* [helpstring][id] */ HRESULT STDMETHODCALLTYPE ITestCtl_test_Proxy( 
    ITestCtl __RPC_FAR * This);


void __RPC_STUB ITestCtl_test_Stub(
    IRpcStubBuffer *This,
    IRpcChannelBuffer *_pRpcChannelBuffer,
    PRPC_MESSAGE _pRpcMessage,
    DWORD *_pdwStubPhase);



#endif 	/* __ITestCtl_INTERFACE_DEFINED__ */



#ifndef __AXSampleLib_LIBRARY_DEFINED__
#define __AXSampleLib_LIBRARY_DEFINED__

/* library AXSampleLib */
/* [helpstring][version][uuid] */ 




EXTERN_C const IID LIBID_AXSampleLib;

EXTERN_C const CLSID CLSID_AXSampleCtl;

#ifdef __cplusplus

class DECLSPEC_UUID("E9CFE582-8937-4F7D-AFA0-00D14B28A191")
AXSampleCtl;
#endif

#ifndef ___ITestCtlEvents_DISPINTERFACE_DEFINED__
#define ___ITestCtlEvents_DISPINTERFACE_DEFINED__

/* dispinterface _ITestCtlEvents */
/* [helpstring][uuid] */ 


EXTERN_C const IID DIID__ITestCtlEvents;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("75667C0A-1FAB-45C5-8B67-B919F6E98338")
    _ITestCtlEvents : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _ITestCtlEventsVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *QueryInterface )( 
            _ITestCtlEvents __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ void __RPC_FAR *__RPC_FAR *ppvObject);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *AddRef )( 
            _ITestCtlEvents __RPC_FAR * This);
        
        ULONG ( STDMETHODCALLTYPE __RPC_FAR *Release )( 
            _ITestCtlEvents __RPC_FAR * This);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfoCount )( 
            _ITestCtlEvents __RPC_FAR * This,
            /* [out] */ UINT __RPC_FAR *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetTypeInfo )( 
            _ITestCtlEvents __RPC_FAR * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo __RPC_FAR *__RPC_FAR *ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE __RPC_FAR *GetIDsOfNames )( 
            _ITestCtlEvents __RPC_FAR * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR __RPC_FAR *rgszNames,
            /* [in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID __RPC_FAR *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE __RPC_FAR *Invoke )( 
            _ITestCtlEvents __RPC_FAR * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS __RPC_FAR *pDispParams,
            /* [out] */ VARIANT __RPC_FAR *pVarResult,
            /* [out] */ EXCEPINFO __RPC_FAR *pExcepInfo,
            /* [out] */ UINT __RPC_FAR *puArgErr);
        
        END_INTERFACE
    } _ITestCtlEventsVtbl;

    interface _ITestCtlEvents
    {
        CONST_VTBL struct _ITestCtlEventsVtbl __RPC_FAR *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _ITestCtlEvents_QueryInterface(This,riid,ppvObject)	\
    (This)->lpVtbl -> QueryInterface(This,riid,ppvObject)

#define _ITestCtlEvents_AddRef(This)	\
    (This)->lpVtbl -> AddRef(This)

#define _ITestCtlEvents_Release(This)	\
    (This)->lpVtbl -> Release(This)


#define _ITestCtlEvents_GetTypeInfoCount(This,pctinfo)	\
    (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo)

#define _ITestCtlEvents_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo)

#define _ITestCtlEvents_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)

#define _ITestCtlEvents_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___ITestCtlEvents_DISPINTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_TestCtl;

#ifdef __cplusplus

class DECLSPEC_UUID("0C05DDCB-049A-42FE-8B0F-05F4AEE99AEA")
TestCtl;
#endif
#endif /* __AXSampleLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

unsigned long             __RPC_USER  BSTR_UserSize(     unsigned long __RPC_FAR *, unsigned long            , BSTR __RPC_FAR * ); 
unsigned char __RPC_FAR * __RPC_USER  BSTR_UserMarshal(  unsigned long __RPC_FAR *, unsigned char __RPC_FAR *, BSTR __RPC_FAR * ); 
unsigned char __RPC_FAR * __RPC_USER  BSTR_UserUnmarshal(unsigned long __RPC_FAR *, unsigned char __RPC_FAR *, BSTR __RPC_FAR * ); 
void                      __RPC_USER  BSTR_UserFree(     unsigned long __RPC_FAR *, BSTR __RPC_FAR * ); 

unsigned long             __RPC_USER  VARIANT_UserSize(     unsigned long __RPC_FAR *, unsigned long            , VARIANT __RPC_FAR * ); 
unsigned char __RPC_FAR * __RPC_USER  VARIANT_UserMarshal(  unsigned long __RPC_FAR *, unsigned char __RPC_FAR *, VARIANT __RPC_FAR * ); 
unsigned char __RPC_FAR * __RPC_USER  VARIANT_UserUnmarshal(unsigned long __RPC_FAR *, unsigned char __RPC_FAR *, VARIANT __RPC_FAR * ); 
void                      __RPC_USER  VARIANT_UserFree(     unsigned long __RPC_FAR *, VARIANT __RPC_FAR * ); 

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif
