<h3>Hi ${name?html}!</h3>
<#if retry?? && retry == true >
    <p>Kurze Erinnerung:</p>
</#if>
<p>Du wurdest zu der Wichtel-Gruppe "${secretSantaGroupTitle?html}" hinzugefügt!</p>
<p>Dein Wichtel-Partner ist <b>${targetPersonName?html}</b> (<a href="${targetPersonMail?html}" target="_blank">${targetPersonMail?html}</a>).</p>

<p>Viel Spaß, alles Liebe, alles Gute und bis zum nächsten Mal!</p>
<br/>
<hr>

<b><p style="font-size: 10px; text-align: center;">Send by <a href="https://the-secret-santa.de" target="_blank">The Secret Santa</a></p></b>