package com.hybridiize.oasisDungeons.data;

public enum EventType {
    REGION_ENTER,
    REGION_EXIT,
    ALL_MOBS_IN_REGION_KILLED,      // All *hostile* mobs (that OasisDungeons is aware of or can find) within 'mobRegion' are gone
    SPECIFIC_MOBS_KILLED_IN_REGION, // X specific mob types killed in 'mobRegion'
    PLAYER_DEATHS_IN_INSTANCE_THRESHOLD,
    BLOCK_PLACED_IN_REGION,
    ITEM_PLACED_IN_CHEST_IN_REGION,
    CHAT_MESSAGE_SENT_MATCHES_PATTERN
    // Add more event types as needed
}