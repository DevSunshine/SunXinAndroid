package com.sunshine.sun.lib.socket.toolbox;
// Copyright (c) 2016 ${ORGANIZATION_NAME}. All rights reserved.

/**
 * Created by 钟光燕 on 2016/7/14.
 * e-mail guangyanzhong@163.com
 */
public class StreamBuffer implements IInputBuffer,IOutPutBuffer {

    private BytePool mPool  ;

    protected byte[] buf ;

    protected int count;

    protected int pos;

    public StreamBuffer(BytePool pool){
        this(pool,512) ;
    }

    public StreamBuffer(BytePool pool,int size){
        mPool = pool ;
        buf = mPool.getBuffer(size) ;
    }

    @Override
    public synchronized int read() {
        return pos < count ? buf[pos++] & 0xFF : -1;
    }

    @Override
    public synchronized int read(byte[] buffer) {
        return read(buffer,0,buffer.length);
    }

    @Override
    public synchronized int read(byte[] buffer, int byteOffset, int byteCount) {
        if (this.pos >= this.count) {
            return -1;
        }
        if (byteCount == 0) {
            return 0;
        }
        int copyLen = this.count - pos < byteCount ? this.count - pos : byteCount;
        System.arraycopy(this.buf, pos, buffer, byteOffset, copyLen);
        pos += copyLen;
        return copyLen;
    }

    @Override
    public synchronized int available() {
        return count - pos;
    }

    @Override
    public synchronized byte[] readToByte(){
        byte[] returnBuf = new  byte[count] ;
        read(returnBuf);
        return returnBuf ;
    }

    @Override
    public synchronized void write(int oneByte) {
        expand(1);
        buf[count ++] = (byte) oneByte;
    }

    @Override
    public synchronized void write(byte[] buffer) {
        write(buffer, 0, buffer.length);
    }

    @Override
    public synchronized void write(byte[] buffer, int offset, int len) {
        expand(len);
        System.arraycopy(buffer,offset,buf,pos,len);
//        for (int i = offset; i < offset + len; i++) {
//            write(buffer[i]);
//        }
    }

    private void expand(int i){

        if (count + i > buf.length){
            byte[] newBuf ;
            if (pos > 0){
                int newCount = count - pos ;
                newBuf = mPool.getBuffer(newCount + i);
                System.arraycopy(this.buf, pos, newBuf, 0, newCount);
                count = newCount ;
            }else {
                newBuf = mPool.getBuffer(count + i);
                System.arraycopy(this.buf , 0, newBuf, 0, count);
            }
            pos = 0 ;
            mPool.cacheBuffer(newBuf);
            this.buf = newBuf ;
        }
    }

    public int getLength(){
        return count ;
    }

    public void reset(){
        pos = count = 0 ;
    }

    public void close(){
        mPool.cacheBuffer(buf);
        buf = null ;
        count = pos = 0 ;
    }
}
