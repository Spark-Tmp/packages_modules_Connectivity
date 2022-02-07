/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.nearby;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

/**
 * Represents an element in {@link PresenceCredential}.
 *
 * @hide
 */
public class CredentialElement implements Parcelable {
    private final String mKey;
    private final byte[] mValue;

    private CredentialElement(String key, byte[] value) {
        mKey = key;
        mValue = value;
    }

    @NonNull
    public static final Parcelable.Creator<CredentialElement> CREATOR =
            new Parcelable.Creator<CredentialElement>() {
                @Override
                public CredentialElement createFromParcel(Parcel in) {
                    String key = in.readString();
                    byte[] value = new byte[in.readInt()];
                    in.readByteArray(value);
                    return new CredentialElement.Builder().setElement(key, value).build();
                }

                @Override
                public CredentialElement[] newArray(int size) {
                    return new CredentialElement[size];
                }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeInt(mValue.length);
        dest.writeByteArray(mValue);
    }

    /**
     * Returns the key of the credential element.
     */
    @NonNull
    public String getKey() {
        return mKey;
    }

    /**
     * Returns the value of the credential element.
     */
    @NonNull
    public byte[] getValue() {
        return mValue;
    }

    /**
     * Builder for {@link CredentialElement}.
     */
    public static class Builder {
        private String mKey;
        private byte[] mValue;

        /**
         * Set the key and value for this credential element.
         */
        @NonNull
        public CredentialElement.Builder setElement(@NonNull String key, @NonNull byte[] value) {
            mKey = key;
            mValue = value;
            return this;
        }

        /**
         * Builds a {@link CredentialElement}.
         */
        @NonNull
        public CredentialElement build() {
            Preconditions.checkState(mKey != null && mValue != null,
                    "neither key or value can be null");
            return new CredentialElement(mKey, mValue);
        }
    }
}
