/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.rmbridge.handler;

import static org.openhab.binding.rmbridge.RMBridgeBindingConstants.*;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link RMBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Anthony Law - Initial contribution
 */
public class RMBridgeHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(RMBridgeHandler.class);

    private String onHexStr;

    private String offHexStr;

    private String targetMac;

    private RMBridgeAPI api;

    public RMBridgeHandler(Thing thing) {
        super(thing);
        logger.info("Contructor of RMBridgeHandler");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.info("Handling command");
        if (api == null) {
            logger.error("Not initialized before command!");
            return;
        }

        if (channelUID.getId().equals(CHANNEL_SWITCH)) {
            if (command instanceof OnOffType) {
                OnOffType onOffType = (OnOffType) command;
                if (onOffType == OnOffType.ON) {
                    if (onHexStr != null) {
                        try {
                            api.sendCode(targetMac, onHexStr);
                        } catch (RMBridgeException e) {
                            logger.error("Error when sending on-hex to bridge", e);
                        }
                    } else {
                        logger.warn("on-hex String is null. Skipping");
                    }
                } else if (onOffType == OnOffType.OFF) {
                    if (offHexStr != null) {
                        try {
                            api.sendCode(targetMac, offHexStr);
                        } catch (RMBridgeException e) {
                            logger.error("Error when sending off-hex to bridge", e);
                        }
                    } else {
                        logger.warn("off-hex String is null. Skipping");
                    }
                } else {
                    logger.warn("Unknown OnOffType command received.");
                }
            }
        }
    }

    @Override
    public void initialize() {
        logger.info("Initializing...");
        Configuration conf = this.getConfig();

        String host = objToStr(conf.get(PARAMETER_BRIDGE_HOSTNAME));
        int port = objToInt(conf.get(PARAMETER_BRIDGE_PORT));

        if (port == -1) {
            port = 7474;
        }

        if (host == null || port <= 0 || port > 65536) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Cannot access RMBridge cause the hostname/port configuration are invalid.");
            return;
        }

        api = new RMBridgeAPI("http://" + host + ":" + port);

        onHexStr = objToStr(conf.get(PARAMETER_ON_HEX));
        offHexStr = objToStr(conf.get(PARAMETER_OFF_HEX));
        targetMac = objToStr(conf.get(PARAMETER_TARGET_MAC));

        updateStatus(ThingStatus.ONLINE);
    }

    private int objToInt(Object obj) {
        if (obj instanceof String) {
            return (Integer) obj;
        } else {
            return -1;
        }
    }

    private String objToStr(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return null;
        }
    }
}
