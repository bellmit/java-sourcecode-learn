package com.niimbot.asset.framework.constant;

/**
 * 消息队列常量
 */
public interface MqConstant {
    /**
     * 消息队列主题
     */
    String ASSET_TOPIC = "asset";
    String NIIMBOT_MIDDLEND = "niimbot-middlend";

    /**
     * 待办消息
     */
    String ASSET_TODO_PRODUCER_GROUP = "GID_asset-todo_p";
    String ASSET_TODO_CREATE_CONSUMER_GROUP = "GID_asset-todo-create_c";
    String ASSET_TODO_COMPLETE_CONSUMER_GROUP = "GID_asset-todo-complete_c";
    String ASSET_TODO_DELETE_CONSUMER_GROUP = "GID_asset-todo-delete_c";
    String ASSET_TODO_CREATE_TAG = "create";
    String ASSET_TODO_COMPLETE_TAG = "complete";
    String ASSET_TODO_DELETE_TAG = "delete";
    /**
     * 销售单消息
     */
    String ASSET_SALE_ORDER_PRODUCER_GROUP = "GID_asset-sale-order_p";
    String ASSET_SALE_ORDER_PAID_CONSUMER_GROUP = "GID_asset-sale-order-paid_c";
    String ASSET_SALE_ORDER_PUSH_CONSUMER_GROUP = "GID_asset-sale-order-push_c";
    String ASSET_SALE_ORDER_PAID_TAG = "paid";
    String ASSET_SALE_ORDER_PUSH_TO_MIDDLEND_TAG = "push-to-middlend";

    String TAG_ORDER_SALE_STOCK_OUT_ORDER_ASSET = "tag-order-sale-stock-out-order-asset";
    String MIDDLEND_ORDER_CONSUMER_GROUP = "GID_asset-middlend-order-receive_c";

    /**
     * CRM推送消息
     */
    String ASSET_CRM_PRODUCER_GROUP = "GID_asset-crm_p";
    String ASSET_CRM_PUSH_CONSUMER_GROUP = "GID_asset-crm-push_c";
    String ASSET_CRM_PUSH_TAG = "push";

    /**
     * 外部消息发送
     */
    String ASSET_EXTERNAL_NOTICE_PRODUCER_GROUP = "GID_asset-external-notice_p";
    String ASSET_EXTERNAL_NOTICE_CONSUMER_GROUP = "GID_asset-external-notice_c";
    String ASSET_EXTERNAL_NOTICE_SEND = "send";
}
