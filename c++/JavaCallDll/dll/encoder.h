
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

// "sSrcFileName"（输入参数） : 待加密文件（全路径）
// "sDstFileName"（输入参数） : 加密结果文件（全路径）
// "szMima[8]"（输入参数） : 密码长度为 8 个字节！！！
// 返回值 : 0 表示成功！
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




