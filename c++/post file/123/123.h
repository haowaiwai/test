// 123.h : main header file for the 123 application
//

#if !defined(AFX_123_H__E10AB126_F859_4600_AAB5_DF9C47FCECEB__INCLUDED_)
#define AFX_123_H__E10AB126_F859_4600_AAB5_DF9C47FCECEB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CMy123App:
// See 123.cpp for the implementation of this class
//

class CMy123App : public CWinApp
{
public:
	CMy123App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMy123App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CMy123App)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_123_H__E10AB126_F859_4600_AAB5_DF9C47FCECEB__INCLUDED_)
