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
package fr.paris.lutece.plugins.grukeydiversification.business.customer;

import fr.paris.lutece.plugins.grubusiness.business.customer.Customer;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.test.LuteceTestCase;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang.StringUtils;

/**
 * Test class for a {@code DecryptedCustomer}
 *
 */
public class DecryptedCustomerTest extends LuteceTestCase
{
    private static final String ENCRYPTION_KEY = "plugin-identitystore-encryption-key-test";
    private static final String CUSTOMER_ID_ENCRYPTED = "4Dr48Xtc6qIjgcCkZBbuokCRq2DuB5NW8QZtwRPvxvDtpAs8MlmtOQ==";
    private static final String CUSTOMER_ID_DECRYPTED_1 = "3724a8b2-9d5a-4d7f-81aa-3f2bd4c643d3";

    /**
     * Tests the decryption
     */
    public void testDecryption( )
    {
        Customer customer = new Customer( );
        customer.setId( CUSTOMER_ID_ENCRYPTED );

        Customer customerDecrypted = new DecryptedCustomer( customer, ENCRYPTION_KEY );

        assertThat( customerDecrypted.getId( ), is( CUSTOMER_ID_DECRYPTED_1 ) );
    }

    /**
     * Tests the decryption when the customer id is empty
     */
    public void testDecryptionCustomerIdEmpty( )
    {
        Customer customer = new Customer( );
        customer.setId( StringUtils.EMPTY );

        Customer customerDecrypted = new DecryptedCustomer( customer, ENCRYPTION_KEY );

        assertThat( customerDecrypted.getId( ), is( StringUtils.EMPTY ) );

        customer.setId( null );

        customerDecrypted = new DecryptedCustomer( customer, ENCRYPTION_KEY );

        assertThat( customerDecrypted.getId( ), is( nullValue( ) ) );
    }

    /**
     * Tests the encryption when the encryption key is empty
     */
    public void testDecryptionEncryptionKeyEmpty( )
    {
        Customer customer = new Customer( );
        customer.setId( CUSTOMER_ID_ENCRYPTED );

        try
        {
            new DecryptedCustomer( customer, null );
            fail( "Expected an AppException to be thrown" );
        }
        catch( AppException e )
        {
            // correct behaviour
        }

        try
        {
            new DecryptedCustomer( customer, StringUtils.EMPTY );
            fail( "Expected an AppException to be thrown" );
        }
        catch( AppException e )
        {
            // correct behaviour
        }
    }

    /**
     * Tests that the decryption of an {@code EncryptedIdentityDto} gives the original IdentityDto
     */
    public void testIso( )
    {
        Customer customer = new Customer( );
        customer.setId( CUSTOMER_ID_ENCRYPTED );

        Customer customerEncrypted = new EncryptedCustomer( new DecryptedCustomer( customer, ENCRYPTION_KEY ), ENCRYPTION_KEY );

        assertThat( customerEncrypted.getId( ), is( CUSTOMER_ID_ENCRYPTED ) );
    }
}
