package com.barbogogo.leedreader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

public class CountService extends JobIntentService
{
	private static finalint UNIQUE_JOB_ID = 1337;
	private DataManagement dataManagement;
	private int unreadCount;

	public CountService()
	{
		super("CountService");
		dataManagement = new DataManagement(this);
		unreadCount = 0;
	}

	static void enqueueWork(Context ctxt) {
		enqueueWork(ctxt, CountService.class, UNIQUE_JOB_ID,
		new Intent(ctxt, CountService.class));
	}

	@Override
    protected void onHandleWork(Intent workIntent) {
        if (dataManagement.getUnreadCount == 0)
        	unreadCount = dataManagement.retrieveUnreadCount();
    }


}