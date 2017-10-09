
/*
got some help from "http://www.webdeveloper.com/forum"
*/
var questions = [
       [
	   /* here we will move asked (whether answered or not) questions */
	   ],
       [["What is 10 + 4?", "12", "14", "16", "B"],"false",2],
	   [["What is 20 - 9?", "7", "13", "11", "C"],"false",3],
	   [["What is 7 x 3?", "21", "24", "25", "A"],"false",1],
	   [["What is 8 / 2?", "10", "2", "4", "C"],"false",3],
	   [["Who said \"I'll be back\"?", "Rambo", "Terminator", "Robocop", "Dracula"],"false",2],
	   [["What is the best Javascript related site over the Internet?", "Webdeveloper.com", "Microsoft.com", "Google.com", "http://127.0.0.0"],"false",1]
    ],
	to='',
	sec=10,
	A;
/* useful funcs */
function _(x){return document.getElementById(x);}
function getRandomInt(min,max){return Math.floor(Math.random() * (max - min)) + min;}
function in_array(what,where){for(var i=0; i < where.length; i++)if(what == where[i])return true;return false;}

function ask(){
var len=questions.length;
if(len==1){
var answers=questions[0],
    a_len=answers.length,
	cor=0,
	incor=0,
	msg='';
	
	for(var z=0; z < a_len; z++){
	if(in_array('true',answers[z])){cor++;}
	else{incor++;}
	}
	
msg='You have given '+cor+' correct and '+incor+' incorrect answers for '+a_len+' questions Quiz.<br />';
_('test').innerHTML=msg;
if(_('nw').checked){
var win=window.open('','resultWin','width=500,height=500,top=0,left=0,statusbar=no,searchbar=no,titlebar=no,toolbar=no,location=no,scrollbars=no');
win.document.write('<center><div style="padding:150px 20px;">'+msg+'</div><a href="#null" onclick="window.close();">Close</a></center>');
win.focus();
win.moveBy((screen.width-500)/2,(screen.height-500)/3);
}
return;
}
else{
var answered=questions[0].length,/* how many questions already answered */
    temp=questions.slice(1),/* get a copy of questions w/o first element */
	total=answered + temp.length,/* how many questions in the Quiz */
	index=getRandomInt(0,temp.length),
    Q=A=temp[index],/* pick a random element from temp */
	Q_answer_index=Q[2], /* index of the correct answer */
	q_text=Q[0][0],/* question text */
	q_answers=Q[0].slice(1),/* possible answers */
	test=_('test'),
	i=0,/* counter */
	user_input='false';/* user answer */
	
	test.innerHTML='<div>You have answered '+answered+' questions from '+total+'</div><h3>'+q_text+'</h3><div id="tt"></div>';
	
	for(; i<q_answers.length; i++){
	var val=(i+1==Q_answer_index) ? 'true' : 'false';
	test.innerHTML+='<b>'+(i+1)+'.</b>&nbsp;<input type="radio" name="choices" value="'+val+'" title="'+val+'" />&nbsp;<i>'+q_answers[i]+'</i><br />';
	}
	
	test.innerHTML+='<br /><button>Submit Answer</button>';
	
	var opts=test.querySelectorAll('[type="radio"]'),
	    btn=test.querySelector('button');
	
	for(var k=0; k<opts.length; k++){
	opts[k].onchange=function(){user_input=this.value;}
	}
	
	btn.onclick=function(){
	A[1]=user_input;
	clearTimeout(to);
	answer(index+1);
	}
	
	timer(sec,index+1);
}

}

function timer(val,ind){
var ending=(val > 1) ? 's' : '';
var txt='<b class="r">'+( val>9 ? val : ('0'+val) )+'</b> second'+ending+' left';
if(val > 0){
_('tt').innerHTML=txt;
val--;
to=setTimeout('timer('+val+','+ind+')',1000);
}
else{
_('tt').innerHTML='Time is up';
clearTimeout(to);
answer(ind);
return;
}
}

function answer(ind){
questions[0].push(A);
questions.splice(ind,1);
setTimeout('ask()',200);
}

onload=ask;