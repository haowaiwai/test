<script type="text/javascript">
function SetHome() {
    if (document.all) {
        document.body.style.behavior = 'url(#default#homepage)';
        document.body.setHomePage("http://www.nanmadeit.com");
    } else if (window.sidebar) {
        if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("�����������֧���Զ������ղأ����ֶ����ã�", "��ʾ��Ϣ");
                history.go(-1);
            }
        }
        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
        prefs.setCharPref('browser.startup.homepage', window.location.href);
    }
}

function SetFavorite(){
	url = "http://www.nanmadeit.com";
	try{
		window.external.AddFavorite(url,document.title);
	}
	catch(e){
		alert("�밴 Ctrl-D �ղر�վ");
	}
} 
</script>
<table id="t2"  width="910" height="111" border="0" cellpadding="0" cellspacing="0" background="http://www.nanmadeit.com/img/xiabg.png" align="center">
<tr>
<td width="600"></td>
<td><a href="http://nanmadeit.taobao.com"><img src="http://www.nanmadeit.com/img/f01.png" width="44" border="0"></img></a></td>
<td><a href="javascript:SetFavorite()"><img src="http://www.nanmadeit.com/img/g01.png" border="0"></img></a></td>
<td><a href="http://www.nanmadeit.com/lianxi/lianxi.php"><img src="http://www.nanmadeit.com/img/h01.png" border="0"></img></td>
<td><a href="javascript:SetHome()"><img src="http://www.nanmadeit.com/img/i01.png" border="0"></img></a></td>
</tr>
</table>
<script type="text/javascript">
$(document).ready(function (){
	$("#t2 img").bind("mouseover",function(){
			var src=$(this).attr("src");
			$(this).attr("src",src.replace("01","02"));			
		}).bind("mouseout",function(){
			var src=$(this).attr("src");
			$(this).attr("src",src.replace("02","01"));
		});	
})
</script>
<table width="910" border="0" align="center">
  <tr>
    <td><div align="right"><img src="http://www.nanmadeit.com/img/logo2.png" width="354" height="34" /></div></td>
  </tr>
</table>
<table width="910" border="0" align="center">
  <tr>
    <td><div align="center">
      <p class="STYLE3">--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p>
      <p class="STYLE2">���̵�˿͸�����Ϣ�����ᱻй©�������κλ����͸���<br />
        ���̵�logo��ͼƬ���Ѿ����뱣����������Ȩ����ʹ�� <br />
        ���κι�����������ϵ�������߿ͷ� ����ʱ�䣺��һ������ 8:00��23:00<br />
        ��ICP��10034160</p>
    </div></td>
  </tr>
</table>