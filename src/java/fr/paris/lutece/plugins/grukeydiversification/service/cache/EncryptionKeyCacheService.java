/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.grukeydiversification.service.cache;

import java.util.Locale;

import fr.paris.lutece.plugins.grukeydiversification.business.EncryptionKey;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;

/**
 * <p>
 * This class represents a cache service for encryption keys.
 * </p>
 * <p>
 * Designed as a singleton
 * </p>
 *
 */
public final class EncryptionKeyCacheService extends AbstractCacheableService
{
    private static final String SERVICE_NAME = "GruKeyDiversification.EncryptionKeyCacheService";
    private static final String CACHE_KEY_PREFIX = "[GruKeyDiversification:EncryptionKey]";

    /**
     * Constructor
     */
    private EncryptionKeyCacheService( )
    {
        super( );
        initCache( );
    }

    /**
     * Gives the singleton instance
     * 
     * @return the instance
     */
    public static EncryptionKeyCacheService getInstance( )
    {
        return EncryptionKeyCacheServiceHolder._instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName( )
    {
        return SERVICE_NAME;
    }

    /**
     * Removes an encryption key from the cache
     * 
     * @param encryptionKey
     *            the encryption key to remove
     */
    public void remove( EncryptionKey encryptionKey )
    {
        removeKey( getCacheKey( encryptionKey ) );
    }

    /**
     * Gives the cache key for the specified encryption key
     * 
     * @param encryptionKey
     *            the encryption key
     * @return the cache key
     */
    public String getCacheKey( EncryptionKey encryptionKey )
    {
        return getCacheKey( encryptionKey.getCode( ) );
    }

    /**
     * Gives the cache key for the specified encryption key code
     * 
     * @param strCode
     *            the encryption key code
     * @return the cache key
     */
    public String getCacheKey( String strCode )
    {
        StringBuilder stringBuilder = new StringBuilder( CACHE_KEY_PREFIX ).append( "[code:" ).append( strCode.toLowerCase( Locale.getDefault( ) ) )
                .append( ']' );

        return stringBuilder.toString( );
    }

    /**
     * This class holds the EncryptionKeyCacheService instance
     *
     */
    private static class EncryptionKeyCacheServiceHolder
    {
        private static final EncryptionKeyCacheService _instance = new EncryptionKeyCacheService( );
    }

}
