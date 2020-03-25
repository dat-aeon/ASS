"use strict";

process.title = 'node-chat';

// Port where we'll run the websocket server
var webSocketsServerPort = 8084;

// websocket and http servers
var webSocketServer = require('websocket').server;
var http = require('http');
const JSON1 = require('circular-json');


// list of currently connected clients (users)
var clients = new Array();

var listroom = new Array();

var hasUser = false;

const pg  = require('pg')


var oldRoom;

var db_host="10.1.9.70";
var db_port="5432";

const opts = {
		logDirectory:'/home/chat-server/log', // NOTE: folder must exist and be writable...
        fileNamePattern:'chat-server-<DATE>.log',
        dateFormat:'YYYY.MM.DD'
};
const log = require('simple-node-logger').createRollingFileLogger( opts );
/**
 * HTTP server
 */
var server = http.createServer(function(request, response) {
	// Not important for us. We're writing WebSocket server, not HTTP server
});
server.listen(webSocketsServerPort, function() {
	log.info("Server Start : On Port - " + webSocketsServerPort + ".");
});

/**
 * WebSocket server
 */

var wsServer = new webSocketServer({
	// WebSocket server is tied to a HTTP server.
	httpServer : server
});

function forEach(array, action) {
	try {
		for ( var i = 0; i < array.length; i++)
			action(array[i], i);
	} catch (exception) {
		if (exception != Break) {
			throw exception;
		}
	}
}
// move connection to assigned room, new roomIndex is also be assigned
function moveToRoom(room, newroom, user) {

	var tmp = [ newroom, [ user ] ];
	var ri = -1, ui = -1;
	
	//console.log(JSON1.stringify(clients[user.ri][1]));
	//console.log("----------------------------");
	
	
	clients[user.ri][1].splice(user.ui, 1);
	clients[user.ri][1].ui -= 1;
	
	for ( var i = user.ui; i < clients[user.ri][1].length; i++) {
		clients[user.ri][1][i].ui -= 1;
		//console.log(JSON1.stringify(clients[user.ri][1]));
		//console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
	//	console.log(JSON1.stringify(clients[user.ri][1][i]));
	//	console.log("$$$$$$$$$$$$$$$$$$$$$$$");
	}
	for ( var i = 0; i < clients.length; i++) {
		if (clients[i][0] == newroom) {
			//console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			ui = clients[i][1].push(user) - 1;
			ri = i;
			//console.log("MMMMMMMMMMM Room "+room+" New Room: "+newroom+" user: "+JSON1.stringify(user));
			//console.log("IIIIII "+ri+" UI: "+ui);
			return [ ri, ui ];
		}

	}
	
	ri = clients.push(tmp) - 1;
	ui = 0;
	//console.log("OOOOOO "+ri+" UI: "+ui);
	return [ ri, ui ];
}

// This callback function is called every time someone tries to connect to
// the WebSocket server
wsServer.on('request', function(request) {
	
	var userName = false;
	var userId = false;
	var conn = getConnection();

	function getConnection() {
		// accept connection
		//console.log("REquest.Origin >>> " + request.origin);
		var connection = request.accept(null, request.origin);
		var roomName = false;
		var roomIndex = 0;
		
		log.info("New Connection is accepted.");
		
		listroom=[];
		userName = true;
		connection.on('message', function(message) {
			//console.log("Connection +++++ " + connection.origin);
			if (message.type === 'utf8') { 
				var t="message="+message.utf8Data+"";
				
			   if (message.utf8Data.indexOf("userName:")==0){ // first message sent by user is
				   //console.log("$$$$$$$$ "+message.utf8Data);
				   userName = message.utf8Data.substring(9,message.utf8Data.indexOf("userId:"));
					userId = message.utf8Data.substring( message.utf8Data.indexOf("userId:")+7);
					this.name = userName;
					log.info("User Connect : '" + userName +"' is successfully connected.");
				} 
			   //cr - current room , or = old room
				else if(message.utf8Data.indexOf("cr:")==0){
					//console.log("@@@@@@@ "+message.utf8Data);
					//console.log("CCCCCCC "+JSON1.stringify(connection));
					log.info("Change Room : Room changing was requested.");
					
					roomName = message.utf8Data.substring( 3,message.utf8Data.indexOf("or:"));
					oldRoom = message.utf8Data.substring( message.utf8Data.indexOf("or:")+3,message.utf8Data.indexOf("userWithAgency:"));
					
					var userWithAgency = message.utf8Data.substring( message.utf8Data.indexOf("userWithAgency:")+15);
					
					hasUser = true;
					
					if(oldRoom == "" ){
						//console.log(roomName);
						//console.log(userName);
						var isExistRoom = false;
						for ( var i = 0; i < clients.length; i++) {
							if (clients[i][0] == roomName) {
								//console.log("Connection  >>>> " + connection);
								isExistRoom = true;
								this.ri = i;
								this.name = userName;
								this.ui = clients[i][1].push(connection) - 1;
								//console.log("!!!!!!!!!!! "+JSON1.stringify(clients[i][0]));
								//console.log("!!!!!!!!!!! "+JSON1.stringify(clients[i][1]));
								break;
							}
						}
						if(isExistRoom == false)
						{
							clients.push([ roomName, [] ]);
							this.ri = clients.length-1;
							this.name = userName;
							//console.log("QQQQQQQQQQQQQQ client length "+clients.length);
							this.ui = clients[clients.length-1][1].push(connection) -1;
							//console.log("XXXXXXXXXXX "+JSON1.stringify(clients[clients.length-1][0]));
							//console.log("XXXXXXXXXXX "+JSON1.stringify(clients[clients.length-1][1]));
						}
						//console.log("*************** "+JSON1.stringify(clients[0]));
						/*console.log("*************** "+JSON1.stringify(connection.ri));
			        	console.log("*************** "+this.name);
			        	console.log("*************** "+JSON1.parse(this.ui));
			        	console.log("++++++++++++++++++++++");*/
					}else{
						var inds = moveToRoom(oldRoom, roomName, connection);
						this.ri = inds[0];
						this.ui = inds[1];
						
						/*console.log("*************** "+JSON1.stringify(connection.ri));
			        	console.log("*************** "+this.name);
			        	console.log("*************** "+JSON1.parse(this.ui));*/
					}
					
					connection.sendUTF(JSON.stringify({
						type : 'room',
						data : userWithAgency
					}));
					this.room = roomName;
					
					log.info("Change Room : Room changing was successfull.");
				}
				 else if(message.utf8Data.indexOf("getRoomList:")==0){
					 
					 const pool = new pg.Pool({
							user: 'ass',
							host: db_host,
							database: 'ass',
							password: 'ass',
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "getRoomList". Error : ' + err);
							  done(err);
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "getRoomList" was successful.');
							  
							  client.query('SELECT sum(count) count,login_id,agency_name,phone_no,finish_flag, group_id, admin_id FROM  (SELECT CASE  WHEN (read_flag= 0 and  op_send_flag=0 )THEN count(m.id) ELSE 0 END count, '+
							    		'phone_no,login_id,agency_name,last_send_time,finish_flag, g.id as group_id, ag.admin_id FROM message_info m '+
									  	'INNER JOIN message_group mg ON m.id=mg.message_id  '+
							    		'INNER JOIN group_info g ON g.id=mg.group_id  '+
							    		'INNER JOIN login_info l ON l.id=g.agency_user_id  '+
							    		'INNER JOIN agency_info a ON a.id=l.agency_id  '+
							    		'LEFT JOIN admin_group ag ON ag.group_id=g.id '+
							    		//'WHERE m.sender<>'+userId+ ' and op_send_flag=0 '+
							    		'GROUP BY g.agency_user_id,phone_no,login_id,read_flag,last_send_time,agency_name,finish_flag,op_send_flag,g.id, ag.admin_id '+
							    		'HAVING count(m.id) >0 ) tbl '+
							    		'GROUP BY login_id,phone_no,last_send_time,agency_name,finish_flag,group_id, admin_id ORDER BY last_send_time', [], function(err, result) {						      if(err) {
						    	  log.error('Query Failed : Select query for "getRoomList". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  
						    		  var roomList = new Array();
						    		  //log.info('Result Row count :'+result.rows.length);
				    				  for(var i =0 ;i<result.rows.length;i++) {	
				    					  var finishFlag = 0;
				    					  var adminId = 0;
				    					  var group_id = result.rows[i].group_id;
				    					  if(result.rows[i].finish_flag == null){
				    						  client.query('INSERT INTO admin_group(admin_id,group_id,finish_flag) VALUES ($1,$2,$3)', [userId,group_id,0], function(err, result) {
								    			  if(err) {
								    				  log.error('Query Failed 188: INSERT query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "getRoomList". Error : ' + err);
								    			  }
								    			  else{
								    				  log.info('Query 191 : INSERT query for "getRoomList": INSERT INTO admin_group(admin_id,group_id,finish_flag) VALUES ('+userId+','+group_id+','+finishFlag+')');
									    				 
								    				  client.query('COMMIT', function(err) {
								    					  if(err) {
								    						  log.error('Query Failed 195: COMMIT of query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "getRoomList". Error : ' + err);
								    					  }
												          
												      });
								    				  
								    				  finishFlag = 0;
								    				  adminId = userId;
								    			  }
								    		  });
				    					  } else {
				    						  finishFlag = result.rows[i].finish_flag;
				    						  adminId = result.rows[i].admin_id;
				    					  }
				    					  //log.info('admin id :'+adminId);
				    					  if(adminId == userId && finishFlag == 0){
					    					  var obj = {
					    							  count :(result.rows[i].count>0)?result.rows[i].count:'',
					    							  phone_no : result.rows[i].phone_no,
					    							  login_id : result.rows[i].login_id,
					    							  agency_name : result.rows[i].agency_name,
					    							  finish_flag : finishFlag
						    						};
					    					  roomList.push(obj);
				    					  }
				    				  }
				    				  
				    				  //log.info('Room Row count :'+roomList.length);
				    				  
				    				  connection.sendUTF(JSON.stringify({
				  						type : 'roomList',
				  						data : roomList
				  					}));
						    	  }
						    	  else{
						    		
						    		  connection.sendUTF(JSON.stringify({
					  						type : 'roomList',
					  						data : ''
					  					}));
						    	  }
						    	
						      }
						    });
							  done();
						  }
				});
					 	
					}
				 else if(message.utf8Data.indexOf("getAllRoomList:")==0){
					
					const pool = new pg.Pool({
						user: 'ass',
						host: db_host,
						database: 'ass',
						password: 'ass',
						port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "getAllRoomList". Error : ' + err);
							done();
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "getAllRoomList" was successful.');
							  
						    client.query('SELECT login_id,agency_name FROM login_info l INNER JOIN agency_info a ON l.agency_id=a.id  WHERE l.isvalid = 1 and a.isvalid=1 ', [], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query for "getAllRoomList". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var roomList = new Array();
				    				  for(var i =0 ;i<result.rows.length;i++) {
				    					  var obj = {
				    							  login_id : result.rows[i].login_id,
				    							  agency_name : result.rows[i].agency_name
					    						};
				    					  roomList.push(obj);
				    				  }
				    				  connection.sendUTF(JSON.stringify({
				  						type : 'allRoomList',
				  						data : roomList
				  					}));
				    				 
						    	  }
						    	  
						      }
						    });
						    done();
						  }
				});
						
					}
				 else if(message.utf8Data.indexOf("msgid:")==0){ // Search message
					
						var msgid = message.utf8Data.substring( 6,message.utf8Data.indexOf("room:"));
						roomName = message.utf8Data.substring( message.utf8Data.indexOf("room:")+5);
						
						var strSearch = "";
						if(msgid){
							
							strSearch = " AND mi.id < "+msgid;
						}
						const pool = new pg.Pool({
							user: 'ass',
							host: db_host,
							database: 'ass',
							password: 'ass',
							port: db_port});
						pool.connect(function(err, client, done) {
							  if(err) {
								  log.error('Could not connect to PostgreSQL server for "msgid". Error : ' + err);
								  
								done();
							  }else{
								  log.info('Connecting to PostgreSQL server for "msgid" was successful.');
								  
								  client.query('SELECT id FROM login_info WHERE login_id = $1', [roomName], function(err, result) {
							      if(err) {
							    	  log.error('Query Failed : Select query "SELECT id FROM login_info WHERE login_id = $1" for "msgid". Error : ' + err);
							      }
							      else{
							    	 
							    	  if(result.rows[0] != undefined){
							    		  var agency_user_id = result.rows[0].id;
							    		  client.query("SELECT mi.id as msgid,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,login_id,op_send_flag,mi.id "+
							    				  "FROM message_info mi INNER JOIN message_group mg ON mi.id=mg.message_id "+
							    				  "INNER JOIN group_info g ON g.id=mg.group_id INNER JOIN login_info l ON l.id=mi.sender "+
							    				  "WHERE agency_user_id=$1 AND op_send_flag=0 "+strSearch+
							    				  "UNION "+
							    				  "SELECT mi.id as msgid,message_content,to_char(send_time, 'DD MON YY HH:MI am') send_time,login_id,op_send_flag,mi.id "+
							    				  "FROM message_info mi INNER JOIN message_group mg ON mi.id=mg.message_id  "+
							    				  "INNER JOIN group_info g ON g.id=mg.group_id INNER JOIN management_user_info u ON u.id=mi.sender "+
							    				  "WHERE agency_user_id=$1 AND op_send_flag=1 "+strSearch+
							    				  "ORDER BY msgid DESC LIMIT 10 ", [agency_user_id], function(err, result) {
							    			  if(err) {
										    	  log.error('Query Failed : Messages select query "SELECT mi.id as msgid,message_content,to_char(send_time,..." for "msgid". Error : ' + err); 
							    			  }
							    			  else{
							    				  
							    				  var oldMessage = new Array();
							    				  var msgids = "";
							    				  for(var i =0 ;i<result.rows.length;i++) {
							    					  msgids += result.rows[i].id +",";
							    					  var obj = {
							    							    time : result.rows[i].send_time,
								    							text : result.rows[i].message_content,
								    							author : result.rows[i].login_id,
								    							op_send_flag : result.rows[i].op_send_flag,
								    							id : result.rows[i].id
								    						};
							    					  oldMessage.push(obj);
							    				  }
							    				  if(msgids) {
							    					
							    					  msgids = msgids.substr(0, msgids.length-1);
							    					
							    					  client.query("UPDATE message_info m SET read_flag=1 WHERE m.id IN ("+ msgids +") AND m.op_send_flag=0", [], function(err, result) {
										    			  if(err) {
													    	  log.error('Query Failed : Update query "UPDATE message_info m SET read_flag=1..." for "msgid". Error : ' + err);
													    	  
																return client.query('ROLLBACK', function(err) {
															          if(err) {
																    	  log.error('Query Failed : ROLLBACK query of "UPDATE message_info m SET read_flag=1..." for "msgid". Error : ' + err);
															          }
																 });
										    			  }else{
										    				  client.query('COMMIT', function(err) {
														          
														          if(err) {
															    	  log.error('Query Failed : COMMIT query of "UPDATE message_info m SET read_flag=1..." for "msgid". Error : ' + err);
														           
														          }else{
														        	
														          }
														      });
										    				  connection.sendUTF(JSON.stringify({
											  						type : 'oldMessage',
											  						data : oldMessage
											  					}));
										    			  }
								    				  });
							    				  }
							    				  else{
							    					  connection.sendUTF(JSON.stringify({
									  						type : 'oldMessage',
									  						data : oldMessage
									  					}));
							    				  }
							    				  
							    				
							    				 
							    			  }
					    				  }); 
							    	  }
							      }
							    });
							    done();
							  }
					});
				 }
				 else if(message.utf8Data.indexOf("msg:")==0){// Save and broadcast the message
					 
					 var msg = message.utf8Data.substring( 4,message.utf8Data.indexOf("op_send_flag:"));
					 var op_send_flag = message.utf8Data.substring( message.utf8Data.indexOf("op_send_flag:")+13);
						
					 const pool = new pg.Pool({
						user: 'ass',
						host: db_host,
						database: 'ass',
						password: 'ass',
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "msg". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "msg" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "msg". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
						    client.query('SELECT id FROM login_info WHERE login_id = $1 and isvalid=1', [roomName], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : Select query "SELECT id FROM login_info WHERE login_id = $1..." for "msg". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  var agency_user_id = result.rows[0].id;
						    		  var group_id = 0;
						    		   client.query('SELECT id FROM group_info WHERE agency_user_id = $1', [agency_user_id], function(err, result) {
						    			  if(err) {
									    	  log.error('Query Failed : SELECT query "SELECT id FROM group_info WHERE agency_user_id = $1" for "msg". Error : ' + err);
						    			  }
						    			  else{
						    				 //if group id is not exist, will insert.
						    				  if(result.rows[0]==undefined){
						    					  client.query('INSERT INTO group_info(agency_user_id) VALUES ($1) RETURNING id', [agency_user_id], function(err, result) {
									    			  if(err) {
												    	  log.error('Query Failed : INSERT query "INSERT INTO group_info(agency_user_id) VALUES ($1) RETURNING id" for "msg". Error : ' + err);
												    	  return client.query('ROLLBACK', function(err) {
												    		  if(err) {
														    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO group_info(agency_user_id) VALUES ($1) RETURNING id" for "msg". Error : ' + err);
												    		  }
												    	  });
									    			  }
									    			  else{
									    				  group_id = result.rows[0].id;
									    				  if(group_id > 0){
										    				  var message_id ;
										    				  
										    				  client.query('INSERT INTO message_info(message_content,message_type,sender,op_send_flag) VALUES ($1,$2,$3,$4) RETURNING id', [msg,0,userId,op_send_flag], function(err, result) {
												    			  if(err) {
															    	  log.error('Query Failed : INSERT query "INSERT INTO message_info(message_content,message_type,...." for "msg". Error : ' + err);
															    	  
															    	  return client.query('ROLLBACK', function(err) {
															    		  if(err) {
																	    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO message_info(message_content,message_type,...." for "msg". Error : ' + err);
															    		  }
															    	  });
												    			  }
												    			  else{
												    				  message_id = result.rows[0].id;
												    				  client.query('INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)', [group_id,message_id], function(err, result) {
														    			  if(err) {
																	    	  log.error('Query Failed : INSERT query "INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
																	    	  
																	    	  return client.query('ROLLBACK', function(err) {
																			          if(err) {
																				    	  log.error('Query Failed : ROLLBACK query of "INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
																			          }
																		      });
														    			  }
														    		  });
												    				  
												    			  }
												    		  });
										    				 
										    				 
										    				  client.query('COMMIT', function(err) {
														         
														          if(err) {
														        	  log.error('Query Failed : COMMIT query of new message and group info for "msg". Error : ' + err);
														          }else{
														        	
														        	  var obj = {
																				time :  "",
																				text : msg,
																				room : roomName,
																				op_send_flag: op_send_flag,
																				author: userName,
																				message_id: message_id
																			};
																			

																			
																			var json = JSON.stringify({
																				type : 'message',
																				data : obj
																			});
																			
																			
																			for (i = 0; i < clients.length; i++) {
																				
																				if(clients[i][0]==roomName)
																				{
																					
																					this.ri = i;
																					break;
																				}
																			    
																			}
																			for ( var i = 0; i < clients[this.ri][1].length; i++) {
																				
																				clients[this.ri][1][i].sendUTF(json);
																				var output = '';
																				for (var property in clients[this.ri][1]) {
																				  output += property + ': ' + clients[this.ri][1][property]+'; ';
																				}
																			}
														          }
														         
														      });
											    		  }
									    			  }
									    		  });
						    				  }
						    				  else{
						    					  group_id = result.rows[0].id;
							    				
							    				  if(group_id > 0){
								    				  var message_id ;
								    				  client.query('UPDATE group_info SET last_send_time=now() WHERE agency_user_id=$1', [agency_user_id], function(err, result) {
										    			  if(err) {
										    				  log.error('Query Failed : UPDATE query "UPDATE group_info SET last_send_time=now() WHERE agency_user_id=$1" for "msg". Error : ' + err);
										    				  return client.query('ROLLBACK', function(err) {
										    					  if(err) {
										    						  log.error('Query Failed : ROLLBACK query of "UPDATE group_info SET last_send_time=now() WHERE agency_user_id=$1" for "msg". Error : ' + err);
										    					  }
										    				  });
										    			  }
								    				  }); 
								    				  client.query('INSERT INTO message_info(message_content,message_type,sender,op_send_flag) VALUES ($1,$2,$3,$4) RETURNING id', [msg,0,userId,op_send_flag], function(err, result) {
										    			  if(err) {
										    				  log.error('Query Failed : INSERT query "INSERT INTO message_info(message_content,message_type,..." for "msg". Error : ' + err);
																 return client.query('ROLLBACK', function(err) {
															          if(err) {
															        	  log.error('Query Failed : ROLLBACK query of "INSERT INTO message_info(message_content,message_type,..." for "msg". Error : ' + err);
															          }
																 });
										    			  }
										    			  else{
										    				  message_id = result.rows[0].id;
										    				  client.query('INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)', [group_id,message_id], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : INSERT query "INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
												    				  return client.query('ROLLBACK', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : ROLLBACK query "INSERT INTO message_group(group_id,message_id) VALUES ($1,$2)" for "msg". Error : ' + err);
												    					  }
																      });
												    			  }
												    		  });
										    				  
										    				  if(op_send_flag == 1)
										    				  {
											    				  client.query('UPDATE admin_group SET finish_flag=0 WHERE admin_id = $1 and group_id = $2', [userId,group_id], function(err, result) {
													    			  if(err) {
													    				  log.error('Query Failed : UPDATE query "UPDATE admin_group SET finish_flag=0 WHERE admin_id = $1 and group_id = $2" for "msg". Error : ' + err);
													    				  return client.query('ROLLBACK', function(err) {
													    					  if(err) {
													    						  log.error('Query Failed : ROLLBACK query "UPDATE admin_group SET finish_flag=0 WHERE admin_id = $1 and group_id = $2" for "msg". Error : ' + err);
													    					  }
																	      });
													    			  }
													    		  });
										    			  	  }
										    			  }
										    		  });
								    				 
								    				 
								    				  client.query('COMMIT', function(err) {
												         
												          if(err) {
												        	  log.error('Query Failed : COMMIT query for "msg". Error : ' + err);
												          }else{
												        	  var obj = {
																		time : "",
																		text : msg,
																		room : roomName,
																		op_send_flag: op_send_flag,
																		author: userName,
																		message_id: message_id
																	};
																	

																	
																	var json = JSON.stringify({
																		type : 'message',
																		data : obj
																	});
																	
																	
																	for (i = 0; i < clients.length; i++) {
																		
																		if(clients[i][0]==roomName)
																		{
																			this.ri = i;
																			break;
																		}
																	}
																	//console.log("################# "+JSON1.stringify(clients[this.ri]));
																	for ( var i = 0; i < clients[this.ri][1].length; i++) {
																		clients[this.ri][1][i].sendUTF(json);
																		//console.log("!!!!!!!!!!!!clients[this.ri] "+clients[this.ri]+"*************** "+JSON1.stringify(clients[this.ri][1]));
																		//console.log("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
															        	 // console.log("################# "+JSON1.stringify(connection.name));
															        	 // console.log("################# "+JSON1.stringify(connection.ui));
																	}
												          }
												          
												      });
									    		  }
							    			  }
						    			  }
						    		  });
						    		  
						    		 
						    	  }
						    	
						      }
							  
							  
						     
						    });
						   
						  });
						  done();
						  }
					});
						

				}
				 else if(message.utf8Data.indexOf("unReadMesgCount:")==0){// Get unread Message for mobile
					 
					// console.log("message.utf8Data.indexOf(\"unReadMesgCount:\")==0");
					 const pool = new pg.Pool({
							user: 'ass',
							host: db_host,
							database: 'ass',
							password: 'ass',
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
						  	log.error('Could not connect to PostgreSQL server for "unReadMesgCount". Error : ' + err);
							done(err);
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "unReadMesgCount" was successful.');
							  
							client.query('SELECT count(mi.id) FROM ass.message_group mg'
								+' LEFT JOIN ass.group_info gi'
								+' ON gi.id = mg.group_id'
								+' LEFT JOIN ass.message_info mi '
								+' ON mi.id = mg.message_id'
								+' WHERE gi.agency_user_id =$1'
								+' AND mi.read_flag=0'
								+' AND mi.op_send_flag=1', [userId], function(err, result) {
								  if(err) {
								   log.error('Query Failed : SELECT query "SELECT count(mi.id) FROM ass.message_group mg..." for "msgid". Error : ' + err);
								  }
								  else{
									
									  if(result!=undefined){
									
										  var unReadCount = { count: result.rows[0].count};										 
										  connection.sendUTF(JSON.stringify({
											type : 'unReadMesgCountForMobile',
											data : unReadCount
										}));
										 										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'unReadMesgCountForMobile',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				else if(message.utf8Data.indexOf("messageList:")==0){// Get All Message List for mobile
					 
					 //console.log("message.utf8Data.indexOf(\"messageList:\")==0");
					 const pool = new pg.Pool({
							user: 'ass',
							host: db_host,
							database: 'ass',
							password: 'ass',
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
							log.error('Could not connect to PostgreSQL server for "messageList". Error : ' + err);
							done(err);
						  }
						  else{
							log.info('Connecting to PostgreSQL server for "messageList" was successful.');
							
							client.query('SELECT mg.message_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time'
								+' FROM ass.message_group mg'
								+' LEFT JOIN ass.group_info gi' 
								+' ON gi.id = mg.group_id'
								+' LEFT JOIN ass.message_info mi '
								+' ON mi.id = mg.message_id'
								+' WHERE gi.agency_user_id =$1'
								+' ORDER BY mi.send_time DESC LIMIT 120', [userId], function(err, result) {
								  if(err) {
								   
									log.error('Query Failed : SELECT query "SELECT mg.message_id, mi.message_content, mi.message_type, mi.send_time,..." for "msgid". Error : ' + err);
								  
								  }
								  else{
									
									  if(result!=undefined){
									
										var messageList = new Array();
							    				
							    		var msgids = "";
							    		for(var i =0; i < result.rows.length; i++) {
							    			msgids += result.rows[i].id +",";
							    			var obj = {
							    				messageId : result.rows[i].message_id,
								    			messageContent : result.rows[i].message_content,
								    			messageType : result.rows[i].message_type,
												sendTime : result.rows[i].send_time,
												sender : result.rows[i].sender,
								    			opSendFlag : result.rows[i].op_send_flag,
												readFlag : result.rows[i].read_flag,
												readTime : result.rows[i].read_time
								    		};
							    			messageList.push(obj);
							    		}
										  
										  connection.sendUTF(JSON.stringify({
											type : 'messageListData',
											data : messageList
										}));
										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'messageListData',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				else if(message.utf8Data.indexOf("unReadMessageList:")==0){// Get Unread Message List for mobile
					 
					 const pool = new pg.Pool({
							user: 'ass',
							host: db_host,
							database: 'ass',
							password: 'ass',
							port: db_port});
					 pool.connect(function(err, client, done) {
						  if(err) {
						  
							log.error('Could not connect to PostgreSQL server for "privacy". Error : ' + err);
							done(err);
						  }
						  else{
							  
							client.query('SELECT mg.message_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time'
								+' FROM ass.message_group mg'
								+' LEFT JOIN ass.group_info gi' 
								+' ON gi.id = mg.group_id'
								+' LEFT JOIN ass.message_info mi '
								+' ON mi.id = mg.message_id'
								+' WHERE gi.agency_user_id =$1'
								+' AND mi.read_flag = 0'
								+' ORDER BY mi.send_time DESC', [userId], function(err, result) {
								  if(err) {
								   
									log.error('Query Failed : SELECT mg.message_id, mi.message_content, mi.message_type, mi.send_time:: timestamp without time zone, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time FROM ass.message_group mg'
											+' LEFT JOIN ass.group_info gi' 
											+' ON gi.id = mg.group_id'
											+' LEFT JOIN ass.message_info mi '
											+' ON mi.id = mg.message_id'
											+' WHERE gi.agency_user_id =$1'
											+' AND mi.read_flag = 0'
											+' ORDER BY mi.send_time DESC;'+' Error : ' + err);
								  
								  }
								  else{
									
									  if(result!=undefined){
									
										//console.log("unread messge list for mobile "+result.rows.length);
										var messageList = new Array();
							    				
							    		var msgids = "";
							    		for(var i =0; i < result.rows.length; i++) {
							    			msgids += result.rows[i].id +",";
							    			var obj = {
							    				messageId : result.rows[i].message_id,
								    			messageContent : result.rows[i].message_content,
								    			messageType : result.rows[i].message_type,
												sendTime : result.rows[i].send_time,
												sender : result.rows[i].sender,
								    			opSendFlag : result.rows[i].op_send_flag,
												readFlag : result.rows[i].read_flag,
												readTime : result.rows[i].read_time
								    		};
							    			messageList.push(obj);
							    		}
										  
										  connection.sendUTF(JSON.stringify({
											type : 'unReadMessageListData',
											data : messageList
										}));
										 
									  }
									  else{
										
										  connection.sendUTF(JSON.stringify({
												type : 'unReadMessageListData',
												data : ''
											}));
											  
									  }
								  }
						    });
							done();
						}
					});
				}
				 else if(message.utf8Data.indexOf("ChangeFinishFlag:")==0){
					 
					 var finishFlag = message.utf8Data.substring( 17,message.utf8Data.indexOf("cr:"));
					 roomName = message.utf8Data.substring( message.utf8Data.indexOf("cr:")+3);
					 
					 const pool = new pg.Pool({
						user: 'ass',
						host: db_host,
						database: 'ass',
						password: 'ass',
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeFinishFlag". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "ChangeFinishFlag" was successful.');
							  
							  client.query('BEGIN', function(err) {
								  if(err) {
									  log.error('Query Failed : BEGIN query for "ChangeFinishFlag". Error : ' + err);
									  return done(true); //pass non-falsy value to done to kill client & remove from pool
								  }
								  
								  client.query('SELECT id FROM login_info WHERE login_id = $1', [roomName], function(err, result) {
									  if(err) {
										  log.error('Query Failed : SELECT query "SELECT id FROM login_info WHERE login_id = $1" for "ChangeFinishFlag". Error : ' + err);
									  }
						      else{
						    	  if(result!=undefined){
						    		  var agency_user_id = result.rows[0].id;
						    		  var group_id = 0;
						    		  
						    		  client.query('SELECT id FROM group_info WHERE agency_user_id=$1', [agency_user_id], function(err, result) {
						    			  if(err) {
						    				  log.error('Query Failed : SELECT query "SELECT id FROM group_info WHERE agency_user_id=$1" for "ChangeFinishFlag". Error : ' + err);
						    			  }
						    			  else{
						    				  if(result.rows[0]!=undefined){
						    					  group_id = result.rows[0].id;
						    					  client.query('SELECT id FROM admin_group WHERE admin_id = $1 and group_id =$2', [userId,group_id], function(err, result) {
									    			  if(err) {
									    				  log.error('Query Failed : SELECT query "SELECT id FROM admin_group WHERE admin_id = $1 and group_id =$2" for "ChangeFinishFlag". Error : ' + err);
									    			  }
									    			  else{
									    				 //if message_group id is not exist, will insert.
									    				  if(result.rows[0]==undefined){
									    					
										    				  client.query('INSERT INTO admin_group(admin_id,group_id,finish_flag) VALUES ($1,$2,$3)', [userId,group_id,finishFlag], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : INSERT query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "ChangeFinishFlag". Error : ' + err);
												    			  }
												    			  else{
												    				  client.query('COMMIT', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : COMMIT of query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "ChangeFinishFlag". Error : ' + err);
												    					  }
																          else{
																        	  clients[connection.ri][1].splice(connection.ui, 1);
																        	  clients[connection.ri][1].ui -= 1;
																          }
																         
																      });
												    			  }
												    		  });
											    			  	
									    					 }
									    				  else{
									    					  client.query('UPDATE admin_group SET finish_flag=$1 WHERE admin_id = $2 and group_id = $3', [finishFlag,userId,group_id], function(err, result) {
												    			  if(err) {
												    				  log.error('Query Failed : UPDATE query "UPDATE admin_group SET finish_flag=$1 WHERE admin_id = $2 and group_id = $3" for "ChangeFinishFlag". Error : ' + err);
												    			  }
												    			  else{
												    				  client.query('COMMIT', function(err) {
												    					  if(err) {
												    						  log.error('Query Failed : COMMIT of query "UPDATE admin_group SET finish_flag=$1 WHERE admin_id = $2 and group_id = $3" for "ChangeFinishFlag". Error : ' + err);
												    					  }
																          else{
																        	 /* console.log("################# "+JSON1.stringify(connection.ri));
																        	  console.log("################# "+JSON1.stringify(connection.name));
																        	  console.log("################# "+JSON1.stringify(connection.ui));*/
																        	  connection.sendUTF(JSON.stringify({
																					type : 'hideFinishButton',
																					data : 1
																				}));
																        	  
																        	
																        	  clients[connection.ri][1].splice(connection.ui, 1);
																        	  clients[connection.ri][1].ui -= 1;
																          }
																         
																      });
												    			  }
												    		  });
									    				  }
									    			  }
									    		  });
						    				  }
						    				  else{
						    					  client.query('INSERT INTO group_info(agency_user_id) VALUES ($1) RETURNING id', [agency_user_id], function(err, result) {
									    			  if(err) {
									    				  log.error('Query Failed : INSERT query "INSERT INTO group_info(agency_user_id,finish_flag)..." for "ChangeFinishFlag". Error : ' + err);
									    			  }
									    			  else{
									    				  group_id = result.rows[0].id;
									    				  client.query('INSERT INTO admin_group(admin_id,group_id,finish_flag) VALUES ($1,$2,$3)', [userId,group_id,finishFlag], function(err, result) {
											    			  if(err) {
											    				  log.error('Query Failed : INSERT query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "ChangeFinishFlag". Error : ' + err);
											    			  }
											    			  else{
											    				  client.query('COMMIT', function(err) {
																         
															          if(err) {
															        	  log.error('Query Failed : COMMIT of query "INSERT INTO admin_group(admin_id,group_id,finish_flag)..." for "ChangeFinishFlag". Error : ' + err);
															          }
															          else{
															        	 
																			connection.sendUTF(JSON.stringify({
																			type : 'hideFinishButton',
																			data : 1
																		}));
																			clients[connection.ri][1].splice(connection.ui, 1);
																        	clients[connection.ri][1].ui -= 1;
															          }
															         
															      });
											    			  }
											    		  });
									    			  }
									    		  });
						    				  }
						    			  }
						    		  });
						    		  
						    	  }
						    	
						      }
							  
							  
						     
						    });
						   
						  });
						  done();
						  }
					});
						

				}else if(message.utf8Data.indexOf("ChangeFinishFlagByMobile:")==0){
					 
					 //var finishFlag = message.utf8Data.substring( 17,message.utf8Data.indexOf("cr:"));
					 roomName = message.utf8Data.substring(25);
					 
					 const pool = new pg.Pool({
						user: 'ass',
						host: db_host,
						database: 'ass',
						password: 'ass',
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeFinishFlagByMobile". Error : ' + err);
							  done();
						  }else{
							  log.info('Connecting to PostgreSQL server for "ChangeFinishFlagByMobile" was successful.');
							  
							  client.query('BEGIN', function(err) {
								  if(err) {
									  log.error('Query Failed 1038: BEGIN query for "ChangeFinishFlagByMobile". Error : ' + err);
									  return done(true); //pass non-false value to done to kill client & remove from pool
								  }
								  
								  client.query('SELECT id FROM login_info WHERE login_id = $1', [roomName], function(err, result) {
									  if(err) {
										  log.error('Query Failed 1044: SELECT query "SELECT id FROM login_info WHERE login_id = $1" for "ChangeFinishFlagByMobile". Error : ' + err);
									  }
								      else{
								    	  if(result!=undefined){
								    		  
								    		  log.info('Query 1049 : Select query for "ChangeFinishFlagByMobile": SELECT id FROM login_info WHERE login_id ='+roomName);
												 
								    		  var agency_user_id = result.rows[0].id;
								    		  log.info("userId "+userId);
								    		  client.query('SELECT id FROM group_info WHERE agency_user_id=$1', [agency_user_id], function(err, result) {
								    			  if(err) {
								    				  log.error('Query Failed 1056: SELECT query "SELECT id FROM group_info WHERE agency_user_id=$1" for "ChangeFinishFlagByMobile". Error : ' + err);
								    			  }
								    			  else{
								    				  if(result.rows[0]!=undefined){
								    					  
								    					  log.info('Query 1061 : Select query for "ChangeFinishFlagByMobile": SELECT id FROM group_info WHERE agency_user_id='+agency_user_id);
								    					  log.info("agency_user_id "+result.rows[0].id);
											    		  
								    					  client.query('UPDATE admin_group SET finish_flag=$1 WHERE group_id = $2', [0,result.rows[0].id], function(err, result) {
											    			  if(err) {
											    				  log.error('Query Failed 927: UPDATE query "UPDATE admin_group SET finish_flag=0 WHERE group_id = '+result.rows[0].id+'" for "ChangeFinishFlag". Error : ' + err);
											    			  }
											    			  else{
											    				  log.info('Query 1068 : Update query for "ChangeFinishFlag": UPDATE admin_group SET finish_flag=0 WHERE group_id =,');
												    				
											    				  client.query('COMMIT', function(err) {
											    					  if(err) {
											    						  log.error('Query Failed 932: COMMIT of query "UPDATE admin_group SET finish_flag=$1 WHERE admin_id = $2 and group_id = $3" for "ChangeFinishFlag". Error : ' + err);
											    					  }
															          
															      });
											    			  }
											    		  });
								    				  }
								    	
								    			  }
								    		  });
								    		  
								    	  }
						    	
								      }
							  
								  });
						   
							  });
						  done();
						  }
					});
						
				}
				 else if(message.utf8Data.indexOf("ChangeReadFlagWithMsgId:")==0){// Save and broadcast the message
					 
					  var msg_id = message.utf8Data.substring(24);
					
					 const pool = new pg.Pool({
						user: 'ass',
						host: db_host,
						database: 'ass',
						password: 'ass',
						port: db_port});
					
					pool.connect(function(err, client, done) {
						  if(err) {
							  log.error('Could not connect to PostgreSQL server for "ChangeReadFlagWithMsgId". Error : ' + err);
							  done();
						  }
						  else{
							  log.info('Connecting to PostgreSQL server for "ChangeReadFlagWithMsgId" was successful.');
							  
						 
						  client.query('BEGIN', function(err) {
						    if(err) {
						    	log.error('Query Failed : BEGIN query for "ChangeFinishFlag". Error : ' + err);
						    	return done(true); //pass non-falsy value to done to kill client & remove from pool
						    }
						    client.query('UPDATE message_info SET read_flag=1 WHERE id = $1', [msg_id], function(err, result) {
						      if(err) {
						    	  log.error('Query Failed : UPDATE query "UPDATE message_info SET read_flag=1 WHERE id = $1" for "ChangeReadFlagWithMsgId". Error : ' + err);
						      }
						      else{
						    	  if(result!=undefined){
						    		  client.query('COMMIT', function(err) {
									         
								          if(err) {
								        	  log.error('Query Failed : COMMIT of query "UPDATE message_info SET read_flag=1 WHERE id = $1" for "ChangeReadFlagWithMsgId". Error : ' + err);
								          }
								          else{
								        	 
												connection.sendUTF(JSON.stringify({
												type : 'showMessage',
												data : 1
											}));
								          }
								         
								      });
						    		  
						    	  }
						    	
						      }
							  
							  
						     
						    });
						   
						  });
						  done();
						  }
					});
						

				}
			} else{
				
				log.error("BAD REQUEST.");
			}
		});

		// user disconnected
		connection.on('close', function(connection) {
			if (hasUser !== false ) {
				log.info("User Disconnect : '" + userName +"' is disconnected.");
				if(clients[this.ri] != undefined){
					clients[this.ri][1].splice(this.ui, 1);
					for ( var i = this.ui; i < clients[this.ri][1].length; i++) {
						clients[this.ri][1].ui -= 1;
					}
				}
				hasUser=false;
			}
		});
		return connection;
	}
});
