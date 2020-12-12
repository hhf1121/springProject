package com.hhf.rocketMq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

import java.util.concurrent.ConcurrentHashMap;

/*** @author ：楼兰
 * @date ：Created in 2020/11/5
 * @description: 事务消息的配置实现类
 * mq事务：（两阶段提交，只保证producer和broker之间的事务）
 * 1.给broker发送一个半消息
 * 2.执行本地事务方法（成功：再发送一个确认消息）
 * 3.broker收到半消息会定时回调客户端的checkLocalTransaction（成功：确认消息）
 * 4.以上2或3步骤一旦失败，broker撤销半消息
 * 5.以上2或3步骤一旦成功，broker将半消息转换成consumer可见的消息，consumer即可消费
 **/
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class MyTransactionImpl implements RocketMQLocalTransactionListener {
    private ConcurrentHashMap<Object, String> localTrans = new ConcurrentHashMap<>();


    /**
     * 本地事务方法,处理自己的业务逻辑。
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Object id = msg.getHeaders().get("id");
        String destination = arg.toString();
        localTrans.put(id, destination);
        org.apache.rocketmq.common.message.Message message = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(), "UTF-8", destination, msg);
        String tags = message.getTags();
//        if (StringUtils.contains(tags, "TagA")) {
//            return RocketMQLocalTransactionState.COMMIT;
//        } /*else if (StringUtils.contains(tags, "TagB")) {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        } */else {
            return RocketMQLocalTransactionState.UNKNOWN;
//        }
//        return RocketMQLocalTransactionState.ROLLBACK;
//        return null;
    }

    /*
     * broker检查客户端本地的事务，是否执行完
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //SpringBoot的消息对象中，并没有transactionId这个属性。跟原生API不一样。
        //String destination = localTrans.get(msg.getTransactionId());
        System.out.println("checkLocalTransaction...");
        return RocketMQLocalTransactionState.COMMIT;
    }
}