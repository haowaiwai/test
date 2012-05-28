// test18Dlg.cpp : implementation file
//

#include "stdafx.h"
#include "test18.h"
#include "test18Dlg.h"


#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

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
// CTest18Dlg dialog

CTest18Dlg::CTest18Dlg(CWnd* pParent /*=NULL*/)
	: CDialog(CTest18Dlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CTest18Dlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	HRESULT hr =m_pDocHead.CreateInstance(__uuidof(MSXML2::DOMDocument)); //DOMDocument
	if(!SUCCEEDED(hr)) 
	{ 
		return;
	}
	hr =m_pDocProgram.CreateInstance(__uuidof(MSXML2::DOMDocument)); //DOMDocument
	if(!SUCCEEDED(hr)) 
	{ 
		return;
	}
	hr =m_pDocService.CreateInstance(__uuidof(MSXML2::DOMDocument)); //DOMDocument
	if(!SUCCEEDED(hr)) 
	{ 
		return;
	}
}

void CTest18Dlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTest18Dlg)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CTest18Dlg, CDialog)
	//{{AFX_MSG_MAP(CTest18Dlg)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CTest18Dlg message handlers

BOOL CTest18Dlg::OnInitDialog()
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
	GetHeadXML();
	GetServiceXML();
	GetProgramXML();
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CTest18Dlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CTest18Dlg::OnPaint() 
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
HCURSOR CTest18Dlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CTest18Dlg::GetProgramXML()
{	
	try
	{
		if(m_pDocProgram->load(L"d:\\data\\program.xml")==VARIANT_TRUE)
		{
			m_pRootProgram = m_pDocProgram->GetdocumentElement();
			MSXML2::IXMLDOMNodeListPtr pList = m_pRootProgram->GetchildNodes();
			int iLen = pList->Getlength();
			for(int i=0;i<iLen;i++)
			{
				MSXML2::IXMLDOMElementPtr pNode = pList->Getitem(i);
				MSXML2::IXMLDOMNamedNodeMapPtr pXMLDOMNamedNodeMap = pNode->Getattributes();
				if(pXMLDOMNamedNodeMap != NULL)
				{
					MSXML2::IXMLDOMNodePtr pXMLDOMNodePtr = pXMLDOMNamedNodeMap->getNamedItem(_bstr_t("id"));   
					if(pXMLDOMNodePtr != NULL)
					{
						_bstr_t szID = pXMLDOMNodePtr->GetnodeValue();
						_bstr_t querystr = L"//SI/CHANNEL[@id='" + szID + "']";
						MSXML2::IXMLDOMNodePtr pXMLDOMNodePtr2 = m_pRootService->selectSingleNode(querystr);
						if(pXMLDOMNodePtr2 != NULL)
						{
							MSXML2::IXMLDOMNodeListPtr pSubList = pXMLDOMNodePtr2->GetchildNodes();
							if(pSubList != NULL)
							{
								pNode->setAttribute("name","¹þ¹þ");
								long iSubLen = pSubList->Getlength();
								for(long j=0;j < iSubLen;j++)
								{
									MSXML2::IXMLDOMNodePtr pXMLDOMNodePtr3 = pSubList->Getitem(j);
									MSXML2::IXMLDOMNodePtr pXMLDOMNodePtr4 = pXMLDOMNodePtr3->cloneNode(VARIANT_TRUE);
									pNode->appendChild(pXMLDOMNodePtr4);
								}
							}
						}
					}
				}
			}
		}
		m_pRootHead->appendChild(m_pRootProgram);
		m_pDocHead->save(L"d:\\data\\1.xml");
	}
	catch (_com_error& e)
	{
		AfxMessageBox(e.ErrorMessage());
	}
}

void CTest18Dlg::GetHeadXML()
{
	if(m_pDocHead->load(L"d:\\data\\head.xml")==VARIANT_TRUE)
	{
		m_pRootHead = m_pDocHead->GetdocumentElement();
	}
}

void CTest18Dlg::GetServiceXML()
{
	if(m_pDocService->load(L"d:\\data\\channel.xml")==VARIANT_TRUE)
	{
		m_pRootService = m_pDocService->GetdocumentElement();
	}
}