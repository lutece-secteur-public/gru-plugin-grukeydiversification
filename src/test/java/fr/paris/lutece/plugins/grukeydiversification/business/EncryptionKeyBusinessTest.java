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

package fr.paris.lutece.plugins.grukeydiversification.business;

import fr.paris.lutece.plugins.grukeydiversification.business.exception.EncryptionKeyAlreadyExistException;
import fr.paris.lutece.plugins.grukeydiversification.service.cache.EncryptionKeyCacheService;
import fr.paris.lutece.test.LuteceTestCase;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test class for EncryptionKeyHome
 *
 */
public class EncryptionKeyBusinessTest extends LuteceTestCase
{
    private static final String CODE1 = "Code1";
    private static final String CODE2 = "Code2";
    private static final String KEY1 = "Key1";
    private static final String KEY2 = "Key2";

    /**
     * Tests the CRUD access
     * 
     * @throws Exception
     *             if there is an exception during the test
     */
    public void testBusiness( ) throws Exception
    {
        // Initialize an object
        EncryptionKey encryptionKey = new EncryptionKey( );
        encryptionKey.setCode( CODE1 );
        encryptionKey.setKey( KEY1 );

        // Create test
        EncryptionKeyHome.create( encryptionKey );
        EncryptionKey encryptionKeyStored = EncryptionKeyHome.findByPrimaryKey( encryptionKey.getId( ) );
        assertThat( encryptionKeyStored.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyStored.getKey( ), is( encryptionKey.getKey( ) ) );

        // Update test
        encryptionKey.setCode( CODE2 );
        encryptionKey.setKey( KEY2 );
        EncryptionKeyHome.update( encryptionKey );
        encryptionKeyStored = EncryptionKeyHome.findByPrimaryKey( encryptionKey.getId( ) );
        assertThat( encryptionKeyStored.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyStored.getKey( ), is( encryptionKey.getKey( ) ) );

        // List test
        EncryptionKeyHome.getEncryptionKeysList( );

        // Delete test
        EncryptionKeyHome.remove( encryptionKey.getId( ) );
        encryptionKeyStored = EncryptionKeyHome.findByPrimaryKey( encryptionKey.getId( ) );
        assertThat( encryptionKeyStored, is( nullValue( ) ) );

    }

    /**
     * Tests the uniqueness of the encryption key code
     * 
     * @throws Exception
     *             if there is an error during the test
     */
    public void testCodeUniqueness( ) throws Exception
    {
        // Create 2 keys with the same code
        EncryptionKey encryptionKey1 = new EncryptionKey( );
        encryptionKey1.setCode( CODE1 );
        encryptionKey1.setKey( KEY1 );
        EncryptionKeyHome.create( encryptionKey1 );

        EncryptionKey encryptionKey2 = new EncryptionKey( );
        encryptionKey2.setCode( CODE1 );
        encryptionKey2.setKey( KEY1 );

        try
        {
            EncryptionKeyHome.create( encryptionKey1 );
            fail( "Expected an EncryptionKeyAlreadyExistException to be thrown" );
        }
        catch( EncryptionKeyAlreadyExistException e )
        {
            // correct behavior
        }

        // Update only the key value
        encryptionKey1.setKey( KEY2 );
        EncryptionKeyHome.update( encryptionKey1 );

        // Update a key with a code which already exists
        encryptionKey2.setCode( CODE2 );
        encryptionKey2.setKey( KEY2 );
        EncryptionKeyHome.create( encryptionKey2 );

        encryptionKey1.setCode( CODE2 );
        encryptionKey1.setKey( KEY1 );

        try
        {
            EncryptionKeyHome.update( encryptionKey1 );
            fail( "Expected an EncryptionKeyAlreadyExistException to be thrown" );
        }
        catch( EncryptionKeyAlreadyExistException e )
        {
            // correct behavior
        }

        EncryptionKeyHome.remove( encryptionKey1.getId( ) );
        EncryptionKeyHome.remove( encryptionKey2.getId( ) );
    }

    /**
     * Tests the cache service
     * 
     * @throws Exception
     *             if there is an error during the test
     */
    public void testCache( ) throws Exception
    {
        EncryptionKeyCacheService cache = EncryptionKeyCacheService.getInstance( );
        cache.enableCache( true );

        // Create test
        EncryptionKey encryptionKey = new EncryptionKey( );
        encryptionKey.setCode( CODE1 );
        encryptionKey.setKey( KEY1 );
        EncryptionKeyHome.create( encryptionKey );

        String strCacheKey = cache.getCacheKey( encryptionKey );
        EncryptionKey encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( strCacheKey );
        assertThat( encryptionKeyFromCache, is( nullValue( ) ) );

        EncryptionKey encryptionKeyStored = EncryptionKeyHome.findByCode( encryptionKey.getCode( ) );

        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( cache.getCacheKey( encryptionKey ) );
        assertThat( encryptionKeyFromCache.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyFromCache.getKey( ), is( encryptionKey.getKey( ) ) );
        assertThat( encryptionKeyStored.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyStored.getKey( ), is( encryptionKey.getKey( ) ) );

        // Update test
        encryptionKey.setCode( CODE2 );
        encryptionKey.setKey( KEY2 );
        EncryptionKeyHome.update( encryptionKey );

        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( strCacheKey );
        assertThat( encryptionKeyFromCache, is( nullValue( ) ) );

        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( cache.getCacheKey( encryptionKey ) );
        assertThat( encryptionKeyFromCache, is( nullValue( ) ) );

        encryptionKeyStored = EncryptionKeyHome.findByCode( encryptionKey.getCode( ) );

        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( cache.getCacheKey( encryptionKey ) );
        assertThat( encryptionKeyFromCache.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyFromCache.getKey( ), is( encryptionKey.getKey( ) ) );
        assertThat( encryptionKeyStored.getCode( ), is( encryptionKey.getCode( ) ) );
        assertThat( encryptionKeyStored.getKey( ), is( encryptionKey.getKey( ) ) );

        // Delete test
        EncryptionKeyHome.remove( encryptionKey.getId( ) );
        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( cache.getCacheKey( encryptionKey ) );
        assertThat( encryptionKeyFromCache, is( nullValue( ) ) );

        encryptionKeyStored = EncryptionKeyHome.findByPrimaryKey( encryptionKey.getId( ) );
        encryptionKeyFromCache = (EncryptionKey) cache.getFromCache( cache.getCacheKey( encryptionKey ) );
        assertThat( encryptionKeyFromCache, is( nullValue( ) ) );
    }

}
