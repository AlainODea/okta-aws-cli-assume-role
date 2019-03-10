/*
 * Copyright 2019 Okta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.tools.authentication;

import com.okta.tools.helpers.CookieHelper;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Adaptor for {@link com.sun.webkit.network.CookieManager} that stores cookies
 */
public final class CookieManager extends CookieHandler {
    // internal class use is intentional: we need RFC6265 cookies which this OpenJFX class handles
    private final CookieHandler cookieHandler = new com.sun.webkit.network.CookieManager();
    private final CookieHelper cookieHelper;

    public CookieManager(CookieHelper cookieHelper) {
        this.cookieHelper = cookieHelper;
    }

    @Override
    public Map<String, List<String>> get(URI uri, Map<String,List<String>> requestHeaders) throws IOException
    {
        return cookieHandler.get(uri, requestHeaders);
    }

    @Override
    public void put(URI uri, Map<String,List<String>> responseHeaders) throws IOException {
        cookieHelper.storeCookies(responseHeaders);
        cookieHandler.put(uri, responseHeaders);
    }
}
