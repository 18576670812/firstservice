/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\tools\\android-sdk\\adt-bundle-windows-x86-20140321\\eclipse\\eclipse_workspace\\firstservice\\src\\com\\whb\\remoteservice\\aidl\\BookQuery.aidl
 */
package com.whb.remoteservice.aidl;
public interface BookQuery extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.whb.remoteservice.aidl.BookQuery
{
private static final java.lang.String DESCRIPTOR = "com.whb.remoteservice.aidl.BookQuery";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.whb.remoteservice.aidl.BookQuery interface,
 * generating a proxy if needed.
 */
public static com.whb.remoteservice.aidl.BookQuery asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.whb.remoteservice.aidl.BookQuery))) {
return ((com.whb.remoteservice.aidl.BookQuery)iin);
}
return new com.whb.remoteservice.aidl.BookQuery.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_queryBookById:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.Bundle _result = this.queryBookById(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.whb.remoteservice.aidl.BookQuery
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public android.os.Bundle queryBookById(java.lang.String Id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Id);
mRemote.transact(Stub.TRANSACTION_queryBookById, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_queryBookById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public android.os.Bundle queryBookById(java.lang.String Id) throws android.os.RemoteException;
}
