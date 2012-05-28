// test18Dlg.h : header file
//

#if !defined(AFX_TEST18DLG_H__2495D177_966F_4CB8_ABAC_314702E2E2E0__INCLUDED_)
#define AFX_TEST18DLG_H__2495D177_966F_4CB8_ABAC_314702E2E2E0__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#import <msxml4.dll>

/////////////////////////////////////////////////////////////////////////////
// CTest18Dlg dialog

class CTest18Dlg : public CDialog
{
// Construction
public:
	CTest18Dlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CTest18Dlg)
	enum { IDD = IDD_TEST18_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTest18Dlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CTest18Dlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
public:
	MSXML2::IXMLDOMDocumentPtr m_pDocHead;
	MSXML2::IXMLDOMElementPtr  m_pRootHead;
	MSXML2::IXMLDOMDocumentPtr m_pDocService;
	MSXML2::IXMLDOMElementPtr  m_pRootService;
	MSXML2::IXMLDOMDocumentPtr m_pDocProgram;
	MSXML2::IXMLDOMElementPtr  m_pRootProgram;
	void GetHeadXML();
	void GetProgramXML();
	void GetServiceXML();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEST18DLG_H__2495D177_966F_4CB8_ABAC_314702E2E2E0__INCLUDED_)
