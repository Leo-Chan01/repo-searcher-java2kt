package com.raym.javatokotlindemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raym.javatokotlindemo.R;
import com.raym.javatokotlindemo.app.Util;
import com.raym.javatokotlindemo.models.Repository;

import java.util.List;

import io.realm.Realm;


public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.MyViewHolder> {

	private static final String TAG = DisplayAdapter.class.getSimpleName();

	private List<Repository> mData;
	private final LayoutInflater inflater;
	private final Context mContext;

	public DisplayAdapter(Context context, List<Repository> items) {
		inflater = LayoutInflater.from(context);
		this.mData = items;
		this.mContext = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Repository current = mData.get(position);
		holder.setData(current);
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	@SuppressLint("NotifyDataSetChanged")
	public void swap(List<Repository> data)
	{
		if (data.size() == 0)
			Util.showMessage(mContext, "No Items Found");
		mData = data;
		notifyDataSetChanged();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		private final TextView name;
		private final TextView language;
		private final TextView stars;
		private final TextView watchers;
		private final TextView forks;
		private Repository current;

		@SuppressLint("QueryPermissionsNeeded")
		public MyViewHolder(View itemView) {
			super(itemView);

			name = itemView.findViewById(R.id.txvName);
			language = itemView.findViewById(R.id.txvLanguage);
			stars = itemView.findViewById(R.id.txvStars);
			watchers = itemView.findViewById(R.id.txvWatchers);
			forks = itemView.findViewById(R.id.txvForks);

			ImageView imgBookmark = itemView.findViewById(R.id.img_bookmark);
			imgBookmark.setOnClickListener(v -> bookmarkRepository(current));

			itemView.setOnClickListener(v -> {
				String url = current.getHtmlUrl();
				Uri webpage = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
				if (intent.resolveActivity(mContext.getPackageManager()) != null) {
					mContext.startActivity(intent);
				}
			});
		}

		public void setData(Repository current) {
			this.name.setText(current.getName());
			this.language.setText(String.valueOf(current.getLanguage()));
			this.forks.setText(String.valueOf(current.getForks()));
			this.watchers.setText(String.valueOf(current.getWatchers()));
			this.stars.setText(String.valueOf(current.getStars()));
			this.current = current;
		}

		private void bookmarkRepository(final Repository current) {

			Realm realm = Realm.getDefaultInstance();
			realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(current), () ->
					Util.showMessage(mContext, "Bookmarked Successfully"), error -> {
				Log.i(TAG, error.toString());
				Util.showMessage(mContext, "Error Occurred");
			});
		}
	}
}
