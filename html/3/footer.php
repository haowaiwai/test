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
                alert("您的浏览器不支持自动加入收藏，请手动设置！", "提示信息");
                history.go(-1);
            }
        }
        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
        prefs.setCharPref('browser.startup.homepage', window.location.href);
    }
}

function SetFavorite(){
	url = "http://www.nanmadeit.com";
	title = "描述"
	try
	{
		if (document.all)
			window.external.AddFavorite(url, title); 
		else if (window.sidebar)
			window.sidebar.addPanel(title, url, "");
	}
	catch(e)
	{
		alert("您的浏览器不支持自动加入收藏，请手动设置！", "提示信息");
	}
} 
</script>
<table id="t2"  width="910" height="111" border="0" cellpadding="0" cellspacing="0" background="http://www.nanmadeit.com/img/xiabg.png" align="center">
<tr>
<td width="600"></td>
<td><a href="http://nanmadeit.taobao.com"><img src="http://www.nanmadeit.com/img/f01.png" width="44" border="0"></img></a></td>
<td><a href="javascript:SetFavorite()"><img src="http://www.nanmadeit.com/img/g01.png" border="0"></img></a></td>
<td><img src="http://www.nanmadeit.com/img/h01.png" border="0"></img></td>
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
      <p class="STYLE2">本商店顾客个人信息将不会被泄漏给其他任何机构和个人<br />
        本商店logo和图片都已经申请保护，不经授权不得使用 <br />
        有任何购物问题请联系我们在线客服 工作时间：周一至周日 8:00－23:00<br />
        京ICP备10034160</p>
    </div></td>
  </tr>
</table>