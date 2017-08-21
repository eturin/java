/*1) Для получения заголовочного файла dll_Main.h нужно скомпилировать java-класс и выполнить команду:
		javah -jni dll.Main 
  2) нужно указать дополнительные каталоги включаемых файлов: 
		C:\Program Files\Java\jdk1.8.0_144\include
		C:\Program Files\Java\jdk1.8.0_144\include\win32
	 тогда все загодовочные файлы станут доступны
  3) компилировать нужно под x64, если jdk 64 разрядное
  4) положить полученную dll в рабочий каталог
  5) http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/jniTOC.html
     https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
     https://www.softlab.ntua.gr/facilities/documentation/unix/java/tutorial/native1.1/TOC.html#implementing
*/
#include "dll_Main.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_dll_Main_add7(JNIEnv * env, jobject obj){
	//получаем указатель на класс и его свойство
	jclass   cls=(*env)->GetObjectClass(env,obj);
	jfieldID fid=(*env)->GetFieldID(env,cls,"cnt","I");

	if (!fid)
		perror("Не удалось получить указатель на свойство.\n");
	else{
		//извлекаем значение поля "cnt"
		jint cnt=(*env)->GetIntField(env,obj,fid);
		printf("Java_dll_Main_add cnt = %d\n", cnt);		
		//увеличиваем значение
		(*env)->SetIntField(env, obj, fid, cnt + 7);
	}
}

JNIEXPORT void JNICALL Java_dll_Main_addVall(JNIEnv * env, jobject obj, jint val){
	//получаем указатель на класс и его свойство
	jclass   cls = (*env)->GetObjectClass(env, obj);
	jfieldID fid = (*env)->GetFieldID(env, cls, "cnt", "I");

	if (!fid)
		perror("Не удалось получить указатель на свойство.\n");
	else{
		//извлекаем значение поля "cnt"
		jint cnt = (*env)->GetIntField(env, obj, fid);
		//увеличиваем значение
		(*env)->SetIntField(env, obj, fid, cnt + val);
	}
}

JNIEXPORT jint JNICALL Java_dll_Main_addToM(JNIEnv * env, jobject obj, jint val){
	jint sum = 0;

	//получаем указатель на класс и его свойство
	jclass   cls = (*env)->GetObjectClass(env, obj);
	jfieldID fid = (*env)->GetFieldID(env, cls, "m", "[I");

	if (!fid)
		perror("Не удалось получить указатель на свойство.\n");
	else{
		//получаем из свойства объект
		jintArray  arr = (jintArray)(*env)->GetObjectField(env, obj, fid);
		
		//получаем непереносимую копию элементов массива в памяти библиотеки
		jint     *body = (*env)->GetIntArrayElements(env, arr, 0);
		jsize      len = (*env)->GetArrayLength(env, arr);

		//увеличиваем значения в локальной копии
		for (jsize i = 0; i < len; ++i)
			sum += body[i] += val;
			

		//переносим локальную копию обратно
		(*env)->ReleaseIntArrayElements(env, arr, body, 0);
	}

	return sum;
}