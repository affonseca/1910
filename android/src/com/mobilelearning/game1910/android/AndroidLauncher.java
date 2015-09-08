package com.mobilelearning.game1910.android;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mobilelearning.game1910.Game1910;

public class AndroidLauncher extends AndroidApplication {
	MyAndroidNet net;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Game1910(), config);
	}

	@Override
	public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
		super.initialize(listener, config);
		net = new MyAndroidNet(this);
		Gdx.net = this.net;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Gdx.net = this.getNet();
	}

	@Override
	public Net getNet() {
		return net;
	}
}
