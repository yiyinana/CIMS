package com.base.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author zjn
 * @date 2016年12月20日
 */

public class ProgressBarThread implements Runnable {

	private ArrayList<Integer> proList = new ArrayList<Integer>();
	private int progress;// 当前进度
	private int totalSize;// 总大小
	private boolean run = true;

	public ProgressBarThread(int totalSize) {
		this.totalSize = totalSize;
		// TODO 创建进度条
	}

	/**
	 * @param progress
	 *            进度
	 */
	public void updateProgress(int progress) {
		synchronized (this.proList) {
			if (this.run) {
				this.proList.add(progress);
				this.proList.notify();
			}
		}
	}

	public void finish() {
		this.run = false;
		// 关闭进度条
	}

	@Override
	public void run() {
		synchronized (this.proList) {
			try {
				while (this.run) {
					if (this.proList.size() == 0) {
						this.proList.wait();
					}
					synchronized (proList) {
						this.progress += this.proList.remove(0);
						// TODO 更新进度条
						System.err.println("当前进度：" + (this.progress / this.totalSize * 100) + "%");

					}
				}
				System.err.println("下载完成");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			File file = new File("E:\\template.docx");
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream("文件保存路径");
			ProgressBarThread pbt = new ProgressBarThread((int) file.length());// 创建进度条
			new Thread(pbt).start();// 开启线程，刷新进度条
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = fis.read(buf)) > -1) {// 循环读取
				fos.write(buf, 0, size);
				pbt.updateProgress(size);// 写完一次，更新进度条
			}
			pbt.finish();// 文件读取完成，关闭进度条
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}