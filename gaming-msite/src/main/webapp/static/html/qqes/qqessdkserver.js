/**
 * 提供给cp引入
 * @author zhoushen
 * @since 2016/11/23
 */
(function($, window){
    QqesSdk = function(option){

    }

    /**
     * 2.0 去掉不必要接口
     */
    QqesSdk.version = '2.0';

    QqesSdk.eventCalls = {};

    var debug = function(title, msg){
        // return true;
        console.log(title);
        console.log(msg);
    }

    //接受接口消息通知
    var receive = function(e){

        debug('接受接口返回数据', e.data);

        if( e.data.event &&  QqesSdk.eventCalls[e.data.event] ){
            var data = '';
            if( e.data.data ){
                data = e.data.data;
            }
            QqesSdk.eventCalls[e.data.event](data);
        }
    }

    /**
     * 分享
     * @param  {[type]}   appkey   sdk游戏appkey
     * @param  {Function} callback 分享成功回调
     */
    QqesSdk.share = function(appkey, callback){
        var data = {
            'event'  : 'share',
            'appkey' : appkey
        };

        QqesSdk.eventCalls['shareback'] = callback;

        window.parent.postMessage(data, '*');
    }

    //邀请
    QqesSdk.invite = function(appkey, callback){
        var data = {
            'event'  : 'invite',
            'appkey' : appkey
        };

        QqesSdk.eventCalls['inviteback'] = callback;

        window.parent.postMessage(data, '*');
    }

    //弹出关注弹窗
    QqesSdk.follow = function(){
        var data = {
            'event'  : 'follow'
        };

        window.parent.postMessage(data, '*');
    }

    //out
    window.QqesSdk = QqesSdk;

    window.addEventListener('message', receive, false);

})(null, window);