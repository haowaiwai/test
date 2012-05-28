// ���� ifdef ���Ǵ���ʹ�� DLL �������򵥵�
// ��ı�׼�������� DLL �е������ļ��������������϶���� DECODER_DLL_EXPORTS
// ���ű���ġ���ʹ�ô� DLL ��
// �κ�������Ŀ�ϲ�Ӧ����˷��š�������Դ�ļ��а������ļ����κ�������Ŀ���Ὣ
// DECODER_DLL_API ������Ϊ�Ǵ� DLL ����ģ����� DLL ���ô˺궨���
// ������Ϊ�Ǳ������ġ�
#ifdef DECODER_DLL_EXPORTS
#define DECODER_DLL_API __declspec(dllexport)
#else
#define DECODER_DLL_API __declspec(dllimport)
#endif

// "sSrcFile"����������� : �����ܵ��ļ�(full path and name) 
// "sDstFile"����������� : ���ܽ���ļ�(full path and name)
// "szMima[8]"����������� : ���볤��Ϊ 8 ���ֽڣ�����
// ����ֵ : 0 ����ɹ�
//
DECODER_DLL_API int DecodeFile(const char *sSrcFile, const char *sDstFile, const unsigned char szMima[8]);


/*
// �����Ǵ� decoder_dll.dll ������
class DECODER_DLL_API Cdecoder_dll {
public:
	Cdecoder_dll(void);
	// TODO: �ڴ�������ķ�����
};

extern DECODER_DLL_API int ndecoder_dll;

DECODER_DLL_API int fndecoder_dll(void);
*/