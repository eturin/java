/*1) ��� ��������� ������������� ����� dll_Main.h ����� �������������� java-����� � ��������� �������:
		javah -jni dll.Main 
  2) ����� ������� �������������� �������� ���������� ������: 
		C:\Program Files\Java\jdk1.8.0_144\include
		C:\Program Files\Java\jdk1.8.0_144\include\win32
	 ����� ��� ������������ ����� ������ ��������
  3) ������������� ����� ��� x64, ���� jdk 64 ���������
  4) �������� ���������� dll � ������� �������
  5) http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/jniTOC.html
     https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
     https://www.softlab.ntua.gr/facilities/documentation/unix/java/tutorial/native1.1/TOC.html#implementing
*/
#include "dll_Main.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_dll_Main_add7(JNIEnv * env, jobject obj){
	//�������� ��������� �� ����� � ��� ��������
	jclass   cls=(*env)->GetObjectClass(env,obj);
	jfieldID fid=(*env)->GetFieldID(env,cls,"cnt","I");

	if (!fid)
		perror("�� ������� �������� ��������� �� ��������.\n");
	else{
		//��������� �������� ���� "cnt"
		jint cnt=(*env)->GetIntField(env,obj,fid);
		printf("Java_dll_Main_add cnt = %d\n", cnt);		
		//����������� ��������
		(*env)->SetIntField(env, obj, fid, cnt + 7);
	}
}

JNIEXPORT void JNICALL Java_dll_Main_addVall(JNIEnv * env, jobject obj, jint val){
	//�������� ��������� �� ����� � ��� ��������
	jclass   cls = (*env)->GetObjectClass(env, obj);
	jfieldID fid = (*env)->GetFieldID(env, cls, "cnt", "I");

	if (!fid)
		perror("�� ������� �������� ��������� �� ��������.\n");
	else{
		//��������� �������� ���� "cnt"
		jint cnt = (*env)->GetIntField(env, obj, fid);
		//����������� ��������
		(*env)->SetIntField(env, obj, fid, cnt + val);
	}
}

JNIEXPORT jint JNICALL Java_dll_Main_addToM(JNIEnv * env, jobject obj, jint val){
	jint sum = 0;

	//�������� ��������� �� ����� � ��� ��������
	jclass   cls = (*env)->GetObjectClass(env, obj);
	jfieldID fid = (*env)->GetFieldID(env, cls, "m", "[I");

	if (!fid)
		perror("�� ������� �������� ��������� �� ��������.\n");
	else{
		//�������� �� �������� ������
		jintArray  arr = (jintArray)(*env)->GetObjectField(env, obj, fid);
		
		//�������� ������������� ����� ��������� ������� � ������ ����������
		jint     *body = (*env)->GetIntArrayElements(env, arr, 0);
		jsize      len = (*env)->GetArrayLength(env, arr);

		//����������� �������� � ��������� �����
		for (jsize i = 0; i < len; ++i)
			sum += body[i] += val;
			

		//��������� ��������� ����� �������
		(*env)->ReleaseIntArrayElements(env, arr, body, 0);
	}

	return sum;
}