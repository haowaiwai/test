#include "com_jeecms_core_util_JavaCallDll.h"
#include "dll\encoder.h"
#include <jni.h>

JNIEXPORT jint JNICALL Java_com_jeecms_core_util_JavaCallDll_EncodeFile
(JNIEnv *env, jobject obj, jstring src, jstring dest, jstring password)
{
	const char* szSource = (*env).GetStringUTFChars(  src, 0 );
	const char* szDest = (*env).GetStringUTFChars( dest, 0 );
	const char* szPassword = (*env).GetStringUTFChars( password, 0 );
	
	
	int iRet= EncodeFile(szSource, szDest, (const unsigned char*)szPassword);
	
	(*env).ReleaseStringUTFChars( src, szSource );
	(*env).ReleaseStringUTFChars( dest, szDest );
	(*env).ReleaseStringUTFChars( password, szPassword );
	
	return iRet;
	
}

JNIEXPORT jint JNICALL Java_com_jeecms_core_util_JavaCallDll_DecodeFile
(JNIEnv *env, jobject obj, jstring src, jstring dest, jstring password)
{
	return 0;
}