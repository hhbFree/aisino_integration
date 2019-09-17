var msg = "";
var user = "";
var target = "";
var mode="connect";
var socket = null;
var logged=false;
function getTime() {
	var time = new Date().getHours() + ":" + new Date().getMinutes()
	+ ":" + new Date().getSeconds() + ":";
	return time;
}
$(function() {
	if (typeof (WebSocket) == "undefined") {
		alert("您的浏览器不支持WebSocket");
		return;
	}
	//按下连接按钮
	$("#btnConnection")
	.click(
			function() {
				if ($("#user").val() != "") {
					user = $("#user").val();
					$("#dis").append(
							"(" + getTime() + user
							+ " is online)<br>");
					//实现化WebSocket对象，指定要连接的服务器地址与端口
					socket = new WebSocket(
							"ws://localhost:8082/ws/"
							+ user);
				} else {
					alert("请输入用户名!");
				}
				//打开事件
				socket.onopen = function() {
					alert(user + "已连接");
					//socket.send(mode+"|"+getTime()+"|"+user);
					append2MsgDisPub(getTime(),"连接成功");
					$("#input").hide();
					$("#logged").show();
					$("#lable").append(user);
					$("#btnConnection").attr("disabled",
					"disabled");
					$("#btnClose").removeAttr("disabled");
					$("#btnSend").removeAttr("disabled");
					logged=true;
					mode="broadcast";
					$("#targetdiv").hide();
					setInterval("getUserList()", 5000);
				};
				//获得消息事件
				socket.onmessage = function(msg) {
					//console.log(msg.data)
                    var msgJson=JSON.parse(msg.data);
					var recvMsg=msgJson.msg;
					console.log(recvMsg);

					//私聊
                    append2MsgDisPri(getTime()+" "+msgJson.from.username+" to "+msgJson.to.username,recvMsg);
					// switch(msg.data){
					// case "sendMsg":
					// 	alert(recvMsg[2]+":"+recvMsg[4]);
					// 	append2MsgDisPri(getTime()+" "+recvMsg[2],recvMsg[4]);
					// 	break;
					// case "broadcast":
					// 	alert(recvMsg[2]+":"+recvMsg[4]);
					// 	append2MsgDisPub(getTime()+" "+recvMsg[2],recvMsg[4]);
					// 	break;
					// case "getUserList":
					// 	var list=recvMsg[4].split(".");
					// 	//alert(recvMsg[4])
					// 	$("#userListUl").empty();
					// 	//alert(list.length)
					// 	for(var i=0;i<=list.length-1;i++){
					// 		append2UserList(list[i]);
					// 	}
					// 	break;
					//
					// }
				};
				//关闭事件
				socket.onclose = function() {
					alert(user + "已断开");
					$("#input").show();
					$("#logged").hide();
					$("#lable").empty();
					$("#btnConnection").removeAttr("disabled");
					$("#btnClose").attr("disabled", "disabled");
					$("#btnSend").attr("disabled", "disabled");
					append2MsgDisPub(getTime(),user+"断开连接");
					mode="connect";
					logged=false;
					clearInterval("getUserList()");
				};
				//发生了错误事件
				socket.onerror = function() {
					alert("发生了错误");
					mode="connect";
					logged=false;
					clearInterval("getUserList()");
				};
			});
	//按下发送按钮
	$("#btnSend").click(
			function() {
				msg = $("#msg").val();
				target = $("#target").val();
				switch(mode){
				case "sendMsg":
					if (target != "") {
						if (msg != "") {
							socket.send("{'toId':"+target+",'msg':'"+msg+"'}");
							append2MsgDisPri(getTime()+" "+user+" to "+target,msg);
						} else {
							alert("请输入要发送的信息");
						}
					} else {
						alert("请输入目标");
					}
					$("#msg").val("");
					break;
				case "broadcast":
					if (msg != "") {
						socket.send("2");
					}
					else {
						alert("请输入要发送的信息");
					}
					//append2MsgDisPub(user,msg);
					$("#msg").val("");
					break;
				case "getUserList":
					socket.send("3");
					$("#msg").val("");
					break;
				}
			
			});
	//按下关闭按钮
	$("#btnClose").click(function() {
		socket.close();
	});
	//按下清除按钮
	$("#clr_pub").click(function() {
		$("#dis_public").empty();
	});
	$("#clr_pri").click(function() {
		$("#dis_private").empty();
	});
	//检查剩余字数
	var text_max = 200;
	$("#count_message").html("还可输入" + text_max + "个字");
	$("#msg").keyup(function() {
		var text_length = $('#msg').val().length;
		var text_remaining = text_max - text_length;
		//alert(text_length);
		$('#count_message').html("还可输入" + text_remaining + "个字");
	});
});
//获得用户列表
function getUserList(){
    $.ajax({
        url: "/message",
        dataType: "json",
        type: "post",
        data: {
            user_name: $("#user_name").val(),
            user_email: $("#user_email").val(),
            password: $("#password").val(),
            phone: $("#phone").val(),
        },
        success: function (data) {
            console.log(data);  //在console中查看数据
        }
    });
}

//插入用户列表
function append2UserList(user){
	$("#userListUl").append("<li class='list-group-item'>"+user+"</li>");
}
//插入消息显示
function append2MsgDisPub(head,message){
	$("#dis_public").append("<h6 class='list-group-item-heading' style='color:blue'>"+head+"</h6><p class='list-group-item-text'>"+message+"</p>");
}
function append2MsgDisPri(head,message){
	$("#dis_private").append("<h6 class='list-group-item-heading' style='color:blue'>"+head+"</h6><p class='list-group-item-text'>"+message+"</p>");
}
function publicMode(){
	$("#targetdiv").hide();
	if(logged==true){
		//alert("public");
		mode="broadcast";
	}
};
function privateMode(){
	$("#targetdiv").show();
	if(logged==true){
		//alert("private");
		mode="sendMsg";
	}
};
function alertMode(){
	alert(logged+","+mode);
}