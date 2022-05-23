package com.orhanobut.hawk;

import android.content.Context;
import android.content.SharedPreferences;

final class SharedPreferencesStorage implements Storage {

  private final SharedPreferences preferences;
  private final boolean useApply;

  SharedPreferencesStorage(Context context, String tag, boolean saveUsingApply) {
    preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
    useApply = saveUsingApply;
  }

  SharedPreferencesStorage(SharedPreferences preferences) {
    this.preferences = preferences;
    this.useApply = false;
  }

  @Override public <T> boolean put(String key, T value) {
    HawkUtils.checkNull("key", key);
    return save(getEditor().putString(key, String.valueOf(value)));
  }

  @SuppressWarnings("unchecked")
  @Override public <T> T get(String key) {
    return (T) preferences.getString(key, null);
  }

  @Override public boolean delete(String key) {
    return save(getEditor().remove(key));
  }

  @Override public boolean contains(String key) {
    return preferences.contains(key);
  }

  @Override public boolean deleteAll() {
    return save(getEditor().clear());
  }

  @Override public long count() {
    return preferences.getAll().size();
  }

  private SharedPreferences.Editor getEditor() {
    return preferences.edit();
  }

  private boolean save(SharedPreferences.Editor editor) {
    if(useApply) {
      editor.apply();
      return true;
    } else {
      return editor.commit();
    }
  }
}
