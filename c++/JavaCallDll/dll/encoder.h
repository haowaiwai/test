
// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the DECODER_EXPORTS
// symbol defined on the command line. this symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// DECODER_API functions as being imported from a DLL, wheras this DLL sees symbols
// defined with this macro as being exported.
#ifdef ENCODER_EXPORTS
#define ENCODER_API __declspec(dllexport)
#else
#define ENCODER_API __declspec(dllimport)
#endif

// "sSrcFileName"����������� : �������ļ���ȫ·����
// "sDstFileName"����������� : ���ܽ���ļ���ȫ·����
// "szMima[8]"����������� : ���볤��Ϊ 8 ���ֽڣ�����
// ����ֵ : 0 ��ʾ�ɹ���
// 
ENCODER_API int EncodeFile(const char *sSrcFileName, const char *sDstFileName, const unsigned char szMima[8]);



/*
// This class is exported from the decoder.dll
class DECODER_API CDecoder {
public:
	CDecoder(void);
	// TODO: add your methods here.
};
*/
//extern DECODER_API int nDecoder;

//DECODER_API int fnDecoder(void);




