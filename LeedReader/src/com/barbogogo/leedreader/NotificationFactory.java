package com.barbogogo.leedreader;

public class NotificationFactory
{
	private static string CHANNEL_ID = "General";
	private static NotificationManagerCompat _notificationManager;
	private static int notificationId;

	private static NotificationManagerCompat getNotificationManager()
	{
		if (_notificationManager == null)
			_notificationManager = NotificationManagerCompat.from(this);
	 	return _notificationManager
	}

	public static void registerChannel()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		    // Create the NotificationChannel, but only on API 26+ because
		    // the NotificationChannel class is new and not in the support library
		    CharSequence name = getString(R.string.channel_name);
		    String description = getString(R.string.channel_description);
		    int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
		    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
		    channel.setDescription(description);
		    // Register the channel with the system
		    getNotificationManager().createNotificationChannel(channel);
		}
		channelRegistered = true;
	}

	public static void updateNotification(int count)
	{
		if (getNotificationManager().getNotificationChannel(CHANNEL_ID) == null)
			registerChannel();

		if (count == 0)
		{
			getNotificationManager().cancel(notificationId);
			notificationId = 0;
		} else 
		{
			if (notificationId == 0)
			{
				Random r = new Random();
				notificationId = r.nextInt(Integer.MAX_VALUE);
			}
			
			Intent intent = new Intent(this, LeedReader.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(String.format(R.string.notification_title, count))
		        .setContentText(String.format(R.string.notification_content, count));
		        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
		        .setContentIntent(pendingIntent)
		        .setAutoCancel(true)
		        .setCategory(NotificationCompat.CATEGORY_EVENT)
		        .setOnlyAlertOnce()
		        .setWhen(System.currentTimeMillis())
		        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

			getNotificationManager().notify(notificationId, mBuilder.build());
		}
	}	
}
