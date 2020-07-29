package com.ciyun.renshe.common.dingding.message;

import com.ciyun.renshe.common.dingding.message.param.MessageBody;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息类型转换
 *
 * @Date 2020/4/11 9:10
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
public class MessageTypeConvert {

    /**
     * 工作通知消息转换
     *
     * @param message
     * @return
     */
    public static OapiMessageCorpconversationAsyncsendV2Request.Msg messageCorpconvert(MessageBody message) {
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        switch (message.getMsgType()) {
            case 1:
                msg.setMsgtype("text");
                OapiMessageCorpconversationAsyncsendV2Request.Text text = new OapiMessageCorpconversationAsyncsendV2Request.Text();
                text.setContent(message.getText().getContent());
                msg.setText(text);
                break;
            case 2:
                msg.setMsgtype("image");
                OapiMessageCorpconversationAsyncsendV2Request.Image image = new OapiMessageCorpconversationAsyncsendV2Request.Image();
                image.setMediaId(message.getImage().getMediaId());
                msg.setImage(image);
                break;
           /* case 3:
                msg.setMsgtype("file");
                OapiMessageCorpconversationAsyncsendV2Request.File file = new OapiMessageCorpconversationAsyncsendV2Request.File();
                file.setMediaId(message.getFile().getMediaId());
                msg.setFile(file);
                break;
            case 4:
                msg.setMsgtype("link");
                OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
                link.setText(message.getLink().getText());
                link.setTitle(message.getLink().getTitle());
                link.setMessageUrl(message.getLink().getMessageUrl());
                link.setPicUrl(message.getLink().getPicUrl());
                msg.setLink(link);
                break;*/
            case 5:
                msg.setMsgtype("action_card");

                OapiMessageCorpconversationAsyncsendV2Request.ActionCard actionCard = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
                actionCard.setTitle(message.getActionCard().getTitle());
                actionCard.setMarkdown(message.getActionCard().getMarkdown());
                actionCard.setSingleTitle(message.getActionCard().getSingleTitle());
                actionCard.setSingleUrl(message.getActionCard().getSingleUrl());

                msg.setActionCard(actionCard);
                break;
            /*case 6:

                msg.setMsgtype("action_card");

                OapiMessageCorpconversationAsyncsendV2Request.ActionCard btnActionCard = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
                btnActionCard.setTitle(message.getBtnActionCard().getTitle());
                btnActionCard.setMarkdown(message.getBtnActionCard().getMarkdown());
                btnActionCard.setBtnOrientation(message.getBtnActionCard().getOrientation());

                List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonListList = new ArrayList<>(message.getBtnActionCard().getBtnLists().size());
                for (BtnList btn : message.getBtnActionCard().getBtnLists()) {
                    OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btnJsonList = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
                    btnJsonList.setTitle(btn.getTitle());
                    btnJsonList.setActionUrl(btn.getActionUrl());
                    btnJsonListList.add(btnJsonList);
                }
                btnActionCard.setBtnJsonList(btnJsonListList);
                msg.setActionCard(btnActionCard);
                break;*/
            default:
                msg = null;
                break;
        }
        return msg;
    }

    /**
     * 群消息转换
     *
     * @param message
     * @return
     */
    public static OapiChatSendRequest.Msg sendChatMessage(MessageBody message) {
        //OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
        switch (message.getMsgType()) {
            case 1:
                msg.setMsgtype("text");
                OapiChatSendRequest.Text text = new OapiChatSendRequest.Text();
                text.setContent(message.getText().getContent());
                msg.setText(text);
                break;
            case 2:
                msg.setMsgtype("image");
                OapiChatSendRequest.Image image = new OapiChatSendRequest.Image();
                image.setMediaId(message.getImage().getMediaId());
                msg.setImage(image);
                break;
            /*case 3:
                msg.setMsgtype("file");
                OapiChatSendRequest.File file = new OapiChatSendRequest.File();
                file.setMediaId(message.getFile().getMediaId());
                msg.setFile(file);
                break;
            case 4:
                msg.setMsgtype("link");
                OapiChatSendRequest.Link link = new OapiChatSendRequest.Link();
                link.setText(message.getLink().getText());
                link.setTitle(message.getLink().getTitle());
                link.setMessageUrl(message.getLink().getMessageUrl());
                link.setPicUrl(message.getLink().getPicUrl());
                msg.setLink(link);
                break;*/
            case 5:
                msg.setMsgtype("action_card");

                OapiChatSendRequest.ActionCard actionCard = new OapiChatSendRequest.ActionCard();
                actionCard.setTitle(message.getActionCard().getTitle());
                actionCard.setMarkdown(message.getActionCard().getMarkdown());
                actionCard.setSingleTitle(message.getActionCard().getSingleTitle());
                actionCard.setSingleUrl(message.getActionCard().getSingleUrl());
                msg.setActionCard(actionCard);
                break;
            /*case 6:

                msg.setMsgtype("action_card");

                OapiChatSendRequest.ActionCard btnActionCard = new OapiChatSendRequest.ActionCard();
                btnActionCard.setTitle(message.getBtnActionCard().getTitle());
                btnActionCard.setMarkdown(message.getBtnActionCard().getMarkdown());
                btnActionCard.setBtnOrientation(message.getBtnActionCard().getOrientation());

                List<OapiChatSendRequest.BtnJson> btnJsonListList = new ArrayList<>(message.getBtnActionCard().getBtnLists().size());
                for (BtnList btn : message.getBtnActionCard().getBtnLists()) {
                    OapiChatSendRequest.BtnJson btnJsonList = new OapiChatSendRequest.BtnJson();
                    btnJsonList.setTitle(btn.getTitle());
                    btnJsonList.setActionUrl(btn.getActionUrl());
                    btnJsonListList.add(btnJsonList);
                }
                btnActionCard.setBtnJsonList(btnJsonListList);
                msg.setActionCard(btnActionCard);
                break;*/
            default:
                msg = null;
                break;
        }
        return msg;
    }
}
