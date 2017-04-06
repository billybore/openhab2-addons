/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.rmbridge;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link RMBridgeBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Anthony Law - Initial contribution
 */
public class RMBridgeBindingConstants {

    public static final String BINDING_ID = "rmbridge";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_REMOTE_SWITCH = new ThingTypeUID(BINDING_ID, "remote-switch");

    // List of all Channel ids
    public final static String CHANNEL_SWITCH = "channel-switch";

    public final static String PARAMETER_BRIDGE_HOSTNAME = "bridge-hostname";

    public final static String PARAMETER_BRIDGE_PORT = "bridge-port";

    public final static String PARAMETER_TARGET_MAC = "target-mac";

    public final static String PARAMETER_ON_HEX = "on-hex";

    public final static String PARAMETER_OFF_HEX = "off-hex";

}
