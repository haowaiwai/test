// hello1Dlg.h : header file
//

#if !defined(AFX_HELLO1DLG_H__86AAA80F_5B57_44ED_9D58_01A4AA02A343__INCLUDED_)
#define AFX_HELLO1DLG_H__86AAA80F_5B57_44ED_9D58_01A4AA02A343__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CHello1Dlg dialog

class CHello1Dlg : public CDialog
{
// Construction
public:
	CHello1Dlg(CWnd* pParent = NULL);	// standard constructor
	LRESULT OnMyMessage(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT onShowTask(WPARAM wParam,LPARAM lParam);

// Dialog Data
	//{{AFX_DATA(CHello1Dlg)
	enum { IDD = IDD_HELLO1_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CHello1Dlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CHello1Dlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	virtual void OnOK();
	afx_msg void OnDestroy();
	afx_msg void OnButton1();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_HELLO1DLG_H__86AAA80F_5B57_44ED_9D58_01A4AA02A343__INCLUDED_)
