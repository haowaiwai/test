// hello1.h : main header file for the HELLO1 application
//

#if !defined(AFX_HELLO1_H__BA656128_B443_4DE0_A467_B9B9125F7726__INCLUDED_)
#define AFX_HELLO1_H__BA656128_B443_4DE0_A467_B9B9125F7726__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CHello1App:
// See hello1.cpp for the implementation of this class
//

class CHello1App : public CWinApp
{
public:
	CHello1App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CHello1App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CHello1App)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_HELLO1_H__BA656128_B443_4DE0_A467_B9B9125F7726__INCLUDED_)
