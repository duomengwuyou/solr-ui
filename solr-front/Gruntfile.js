module.exports = function(grunt) {
	// 定义变量
	var path = {
		src : "src/main/webapp/WEB-INF/devsrc",
		dest : "src/main/webapp/WEB-INF/static",
		pages : "src/main/webapp/WEB-INF/pages"
	}
	
	// 编写Tasks
	grunt.initConfig({
		pkg : grunt.file.readJSON('package.json'),
		path : path,
		
		// 压缩js代码
		uglify : {
			options: {
				banner: '/*! create by <%= grunt.template.today("yyyy-mm-dd") %>*/ \n'
			},
			js : {
				files : [ {
					expand : true,
					cwd : '<%= path.src%>/',
					src : ['js/**/*.js','lib/**/*.js'],
					dest : '<%= path.dest%>/'
				} ]
			}
		},
		
		// 压缩css代码
		cssmin: {
			css : {
				files : [ {
					expand : true,
					cwd : '<%= path.src%>/',
					src : ['css/**/*.css'],
					dest : '<%= path.dest%>/'
				} ]
			}
        },
        
        // 动态检测代码变化
		watch : {
			files : [ '<%=path.src%>/js/**/*.js','<%=path.src%>/css/**/*.css','<%=path.src%>/lib/**/*.js' ],
			tasks : [ 'uglify', 'cssmin' ]
		}
		
		/** 用于将多个js合成一个js
		concat : {
			bar: {
				src:['<%= path.src%>/js/*.js'],
				dest: '<%= path.dest %>/js/all.min.js'
			}
		}
		*/
		
	});

	// 自动加载package.json里的依赖
	require('load-grunt-tasks')(grunt, {
		scope : 'devDependencies'
	});
	
	// 注册Tasks
	grunt.registerTask('default', [ 'uglify', 'cssmin', 'watch']);
};