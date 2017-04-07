//更新教师信息
function updateMessage(type, message) {
	mui.ajax('http://localhost:8080/Select-H5/teacher/updateTeacher', {
		data: {
			id: id,
			type: type,
			message: message
		},
		dataType: 'json', //服务器返回json格式数据
		type: 'post', //HTTP请求类型
		timeout: 10000, //超时时间设置为10秒；
		success: function(data) {
			switch (message){
				case '0':
				$("#" + type).text("男");
					break;
				case '1':
					$("#" + type).text("女");
					break;
				default:
					$("#"+type).text(message);
					break;
			}
		},
		error: function(xhr, type, errorThrown) {
			//异常处理；
			mui.toast("修改失败！ ");
		}
	});
}