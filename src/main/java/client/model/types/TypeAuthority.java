/*
 * Copyright (c) 2017 Mithril coin.
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package client.model.types;

import java.util.ArrayList;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class TypeAuthority implements EosType.Packer {

    private int mThreshold;
    private ArrayList<TypeKeyPermissionWeight> mKeys;
    private ArrayList<TypeAccountPermissionWeight> mAccounts;

    TypeAuthority(int threshold, TypeKeyPermissionWeight oneKey, TypeAccountPermissionWeight onePermission) {
        mThreshold = threshold;
        mKeys = new ArrayList<>();
        mAccounts = new ArrayList<>();

        if ( null != oneKey ) {
            mKeys.add(oneKey);
        }

        if ( null != onePermission ) {
            mAccounts.add(onePermission);
        }
    }

    TypeAuthority(int threshold, String pubKeyInHex, String permission) {
        this( threshold
                ,( null == pubKeyInHex ? null: new TypeKeyPermissionWeight( pubKeyInHex, 1))
                ,( null == permission ? null : new TypeAccountPermissionWeight(permission)) );
    }

    @Override
    public void pack(EosType.Writer writer) {

        writer.putIntLE( mThreshold);
        writer.put( (byte) mKeys.size());

        for (TypeKeyPermissionWeight oneKey : mKeys) {
            oneKey.pack(writer);
        }


        writer.put( (byte) mAccounts.size());

        for (TypeAccountPermissionWeight onePermission : mAccounts) {
            onePermission.pack(writer);
        }
    }
}
