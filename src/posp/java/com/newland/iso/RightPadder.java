/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2008 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.newland.iso;

import com.newland.message.MessageException;


/**
 * Implements the Padder interface for padding strings and byte arrays on the
 * Right.
 * 
 * @author joconnor
 * @version $Revision: 2594 $ $Date: 2008-01-22 14:41:31 -0200 (Tue, 22 Jan 2008) $
 */
public class RightPadder implements Padder
{
    /**
	 * A padder for padding spaces on the right. This is very common in
	 * alphabetic fields.
	 */
    public static final RightPadder SPACE_PADDER = new RightPadder(' ');

    private char pad;

    /**
	 * Creates a Right Padder with a specific pad character.
	 * 
	 * @param pad
	 *            The padding character. For binary padders, the pad character
	 *            is truncated to lower order byte.
	 */
    public RightPadder(char pad)
    {
        this.pad = pad;
    }

    /**
	 * @see org.jpos.iso.Padder#pad(java.lang.String, int, char)
	 */
    public String pad(String data, int maxLength) throws MessageException
    {
        StringBuffer padded = new StringBuffer(maxLength);
        int len = data.length();
        if (len > maxLength)
        {
            throw new MessageException("Data is too long. Max = " + maxLength);
        } else
        {
            padded.append(data);
            for (int i = maxLength - len; i > 0; i--)
            {
                padded.append(pad);
            }
        }
        return padded.toString();
    }

    /**
	 * @see org.jpos.iso.Padder#unpad(java.lang.String, char)
	 */
    public String unpad(String paddedData)
    {
        int len = paddedData.length();
        for (int i = len; i > 0; i--)
        {
            if (paddedData.charAt(i - 1) != pad)
            {
                return paddedData.substring(0, i);
            }
        }
        return "";
    }
}