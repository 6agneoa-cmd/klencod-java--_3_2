
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ما وراء الحكاية | مكتبة القصص</title>
<style>
:root{--bg:#10101b;--card:#1d1d2c;--text:#f6f1ff;--muted:#aaa5bd;--accent:#d9a6ff}
*{box-sizing:border-box}
body{margin:0;background:var(--bg);color:var(--text);font-family:Tahoma,Arial;transition:.2s}
body.light{--bg:#f6f2fa;--card:#fff;--text:#292039;--muted:#71687e;--accent:#8a45b5}
header{padding:25px 16px;text-align:center;background:linear-gradient(135deg,#38224f,#151525)}
header h1{margin:0 0 8px;color:var(--accent)}
header p{color:#ddd0ec;margin:0}
nav{display:flex;gap:8px;justify-content:center;flex-wrap:wrap;padding:14px}
button,select,input,textarea{font:inherit}
button{cursor:pointer;border:0;border-radius:10px;padding:10px 14px;background:var(--accent);color:#21142a;font-weight:bold}
input,select,textarea{background:var(--card);color:var(--text);border:1px solid #555064;border-radius:10px;padding:12px;width:100%}
main{max-width:1000px;margin:auto;padding:12px}
.tools{display:grid;grid-template-columns:1fr 150px;gap:10px;margin-bottom:18px}
h2{font-size:21px}
.grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(145px,1fr));gap:14px}
.book{background:var(--card);border-radius:14px;padding:15px;min-height:210px;display:flex;flex-direction:column;gap:8px;border:1px solid #ffffff12}
.cover{height:95px;border-radius:10px;display:grid;place-items:center;font-size:42px;background:linear-gradient(145deg,#482d62,#171728)}
.book h3{font-size:16px;margin:3px 0}
.book small{color:var(--muted)}
.book .actions{display:flex;gap:6px;margin-top:auto}
.book .actions button{flex:1;padding:9px 4px;font-size:13px}
.secondary{background:var(--card);color:var(--text);border:1px solid #555064}
#reader,#addPanel{display:none;background:var(--card);border-radius:16px;padding:18px;margin:15px 0}
#reader h2{text-align:center;color:var(--accent)}
#storyText{white-space:pre-wrap;line-height:2;font-size:19px}
.readerTools{display:flex;gap:8px;flex-wrap:wrap;margin:12px 0}
#addPanel textarea{min-height:180px;margin:8px 0}
footer{text-align:center;color:var(--muted);padding:25px;font-size:13px}
.empty{color:var(--muted);text-align:center;padding:25px}
</style>
</head>
<body>
<header>
<h1>📚 ما وراء الحكاية</h1>
<p>كل قصة باب، وكل باب يخفي عالمًا آخر.</p>
</header>

<nav>
<button onclick="showLibrary('all')">كل القصص</button>
<button onclick="showLibrary('رعب')">👻 رعب</button>
<button onclick="showLibrary('غموض')">🔎 غموض</button>
<button onclick="showLibrary('رومانسية')">❤️ رومانسية</button>
<button onclick="showLibrary('فانتازيا')">🪄 فانتازيا</button>
<button onclick="showLibrary('مفضلة')">⭐ المفضلة</button>
<button class="secondary" onclick="toggleTheme()">🌓 المظهر</button>
<button onclick="toggleAdd()">＋ أضف قصة</button>
</nav>

<main>
<div class="tools">
<input id="search" placeholder="ابحث عن قصة أو كاتب..." oninput="render()">
<select id="sort" onchange="render()">
<option value="new">الأحدث</option>
<option value="title">حسب العنوان</option>
</select>
</div>

<section id="reader">
<button class="secondary" onclick="closeReader()">← رجوع للمكتبة</button>
<h2 id="readerTitle"></h2>
<p id="readerInfo" style="text-align:center;color:var(--muted)"></p>
<div class="readerTools">
<button onclick="changeFont(1)">تكبير الخط ＋</button>
<button class="secondary" onclick="changeFont(-1)">تصغير الخط −</button>
<button class="secondary" onclick="toggleFavorite(currentId)">⭐ المفضلة</button>
</div>
<article id="storyText"></article>
<div class="readerTools">
<button onclick="nextStory()">القصة التالية ←</button>
<button class="secondary" onclick="closeReader()">إغلاق القراءة</button>
</div>
</section>

<section id="addPanel">
<h2>✍️ إضافة قصة جديدة</h2>
<input id="newTitle" placeholder="عنوان القصة">
<input id="newAuthor" placeholder="اسم الكاتب" style="margin-top:8px">
<select id="newGenre" style="margin-top:8px">
<option>رعب</option><option>غموض</option>
<option>رومانسية</option><option>فانتازيا</option>
<option>جريمة</option><option>دراما</option><option>كوميديا</option>
</select>
<textarea id="newText" placeholder="اكتب القصة هنا..."></textarea>
<button onclick="addStory()">حفظ القصة</button>
<button class="secondary" onclick="toggleAdd()">إلغاء</button>
</section>

<h2 id="sectionTitle">📖 مكتبة القصص</h2>
<div id="books" class="grid"></div>
</main>

<footer>ما وراء الحكاية © مكتبتك الخاصة</footer>

<script>
const starterStories = [
{
id:1,title:"الساعة 3:17",author:"ما وراء الحكاية",genre:"رعب",
text:`في كل ليلة، كانت الساعة تتوقف عند 3:17 صباحًا.

لم يكن في الغرفة شيء يتحرك، لكن صوت خطوات يأتي من الممر. خطوات بطيئة، تتوقف أمام الباب، ثم ثلاث طرقات.

في الليلة السابعة، قرر سامي ألا يختبئ تحت الغطاء. حمل هاتفه وفتح الباب.

كان الممر فارغًا. وعلى الحائط، ظهرت كتابة لم تكن موجودة من قبل:

"لا تفتح الباب في الليلة القادمة."

ضحك سامي وعاد إلى سريره.

في الليلة التالية، عند 3:17، سمع الطرقات الثلاث. لكنه هذه المرة سمع صوتًا خلفه يقول:

"الحمد لله أنك فتحت لي."

لم يكن الباب قد تحرك أصلًا.`
},
{
id:2,title:"الرسالة الأخيرة",author:"كاتب مجهول",genre:"غموض",
text:`وصلت إلى مريم رسالة من رقم أخيها الذي اختفى قبل عام.

كانت الرسالة قصيرة: "لا تثقي بالشخص الذي وجدني."

اتصلت بالرقم، لكن الهاتف كان مغلقًا. ذهبت إلى الشرطة، فأخبروها أن أخاها لم يُعثر عليه قط.

عندما عادت إلى المنزل، وجدت المحقق الذي ساعدها طوال السنة ينتظرها أمام الباب.

نظر إلى هاتفها وسأل بهدوء: "هل وصلتك رسالة؟"

لم تخبره أنها استلمتها.

تراجعت خطوة، وفهمت أن السؤال وحده كان اعترافًا.`
},
{
id:3,title:"مدينة بلا ظلال",author:"ليان",genre:"فانتازيا",
text:`في مدينة إيلارا، كان لكل إنسان ظل يتبع خطواته، إلا نادر.

أخبره العجوز أن ظله لم يمت، بل سُرق وحُبس في برج الملك.

تسلل نادر إلى البرج، وهناك وجد آلاف الظلال تتحرك خلف الزجاج. كان بينها ظل طفل صغير يشبهه تمامًا.

وعندما لمس الزجاج، سمع صوتًا في رأسه:

"إذا أخرجتني، ستنسى الشخص الذي تحبه أكثر من أي شيء."

لم يتراجع نادر. فقد جاء إلى هنا لأنه نسي بالفعل وجه أمه.`
},
{
id:4,title:"مقعد بجانب النافذة",author:"سارة",genre:"رومانسية",
text:`كانت تجلس كل خميس في المقعد نفسه، بجانب نافذة المقهى.

وكان يأتي في الموعد نفسه، يطلب القهوة نفسها، ويترك كتابًا على الطاولة قبل أن يغادر.

لم يتحدثا لشهور، حتى جاء يوم ممطر ووجدت داخل الكتاب ورقة:

"أعرف أنك تقرئين الصفحة الأخيرة كل مرة. أنا أيضًا أفعل ذلك، لأنني أتمنى نهاية مختلفة."

في الخميس التالي، وصلت قبله بدقائق.

ولأول مرة، لم تفتح الكتاب. رفعت رأسها عندما دخل وقالت:

"اجلس. هذه المرة نكتب النهاية معًا."`
},
{
id:5,title:"البيت الذي يتنفس",author:"ما وراء الحكاية",genre:"رعب",
text:`اشترى عادل منزلًا قديمًا بثمن أقل من سعر نافذته.

في أول ليلة، سمع الجدران تصدر صوت شهيق طويل، ثم زفيرًا جعل الستائر تتحرك.

استدعى عامل صيانة، فضحك الرجل وقال إن الأنابيب القديمة تفعل ذلك.

قبل أن يغادر، سأله عادل: "كم عائلة سكنت هنا؟"

توقف الرجل عند الباب.

"عائلات؟ لا أحد سكن هنا. المنزل بُني فوق مستشفى قديم."

في تلك الليلة، لم ينم عادل. وضع أذنه على الجدار.

ومن الداخل، سمع صوتًا هامسًا:

"أخيرًا، شخص يسمعني."`
},
{
id:6,title:"المتهم رقم صفر",author:"آدم",genre:"جريمة",
text:`وصل المحقق إلى مسرح الجريمة، فوجد الغرفة مغلقة من الداخل ولا أثر لدخول أحد.

على الطاولة ورقة تحمل عبارة واحدة: "المتهم رقم صفر."

راجع تسجيلات الكاميرات، فظهر الضحية يدخل الغرفة وحده، ثم يجلس وينظر مباشرة إلى الكاميرا.

قبل موته بدقيقة، قال:

"إذا كنت تشاهد هذا يا محقق، فأنت تبحث عن الشخص الخطأ."

أعاد المحقق التسجيل.

هذه المرة لاحظ انعكاس وجهه في زجاج النافذة خلف الضحية.

كان الانعكاس يبتسم، رغم أن المحقق لم يكن يبتسم.`
},
{
id:7,title:"آخر إنسان على الأرض",author:"يونس",genre:"فانتازيا",
text:`استيقظ يونس على مدينة لا يسمع فيها سوى الريح.

كانت السيارات متوقفة، والبيوت مفتوحة، والطعام لا يزال دافئًا على الطاولات. اختفى الجميع في لحظة واحدة.

في اليوم الثلاثين، اشتغل الراديو القديم وحده.

جاء صوت امرأة يقول: "إذا كنت تسمعني، فلا تذهب إلى البحر."

لكن يونس كان قد رأى ضوءًا عند الشاطئ.

ركض نحوه، فوجد آلاف الأضواء تحت الماء، تتحرك كأنها مدينة كاملة.

ثم جاء الصوت مرة أخرى:

"لقد وجدناك أخيرًا."`
},
{
id:8,title:"كذبة صغيرة",author:"نور",genre:"دراما",
text:`قال لوالده إنه نجح في الامتحان، مع أنه رسب.

لم تكن الكذبة كبيرة، هكذا أقنع نفسه. سيعيد الامتحان ويصلح كل شيء.

لكن والده اشترى له هدية، واتصل بأقاربه ليخبرهم بنجاح ابنه.

كلما حاول الاعتراف، رأى الفرحة في عينيه فتراجع.

بعد أسبوع، وضع الشهادة المزيفة أمامه، واعترف بكل شيء.

صمت الأب طويلًا، ثم قال:

"الرسوب يتصلح بالدراسة. أما لو تعودت تخاف من الحقيقة، فهذه أصعب."

لم تكن الهدية هي ما تذكره لاحقًا، بل أنه لم يُطرد من قلب أبيه بسبب خطئه.`
}
];

let stories = starterStories.map(s=>({...s}));
let favorites = [];
let currentId = null;
let currentGenre = "all";
let fontSize = 19;

try {
  stories = JSON.parse(localStorage.getItem("storyLibrary")) || stories;
  favorites = JSON.parse(localStorage.getItem("storyFavorites")) || [];
  if(localStorage.getItem("storyTheme")==="light")
    document.body.classList.add("light");
} catch(e) {}

function save(){
  try{
    localStorage.setItem("storyLibrary",JSON.stringify(stories));
    localStorage.setItem("storyFavorites",JSON.stringify(favorites));
  }catch(e){
    alert("تعذر الحفظ. قد تكون مساحة التخزين ممتلئة.");
  }
}

function showLibrary(genre){
  currentGenre=genre;
  document.getElementById("reader").style.display="none";
  document.getElementById("sectionTitle").style.display="";
  document.getElementById("books").style.display="grid";
  const names={
    all:"📖 مكتبة القصص",رعب:"👻 قصص الرعب",
    غموض:"🔎 قصص الغموض",رومانسية:"❤️ قصص رومانسية",
    فانتازيا:"🪄 عوالم الفانتازيا",مفضلة:"⭐ قصصي المفضلة"
  };
  document.getElementById("sectionTitle").textContent=names[genre]||genre;
  render();
}

function render(){
  const query=document.getElementById("search").value.trim().toLowerCase();
  const sort=document.getElementById("sort").value;
  let list=stories.filter(s=>{
    const matchesGenre=currentGenre==="all"||
      (currentGenre==="مفضلة"?favorites.includes(s.id):s.genre===currentGenre);
    return matchesGenre &&
      (s.title+" "+s.author+" "+s.genre).toLowerCase().includes(query);
  });

  if(sort==="title") list.sort((a,b)=>a.title.localeCompare(b.title,"ar"));
  else list.reverse();

  const container=document.getElementById("books");
  container.innerHTML="";

  if(!list.length){
    container.innerHTML='<p class="empty">لا توجد قصص هنا. المكتبة لا تقرأ الأفكار بعد!</p>';
    return;
  }

  list.forEach(s=>{
    const card=document.createElement("article");
    card.className="book";
    const cover=document.createElement("div");
    cover.className="cover";
    cover.textContent=({
      رعب:"👻",غموض:"🔎",رومانسية:"💌",
      فانتازيا:"🪄",جريمة:"🕵️",دراما:"🎭",كوميديا:"😂"
    })[s.genre]||"📖";

    const title=document.createElement("h3");
    title.textContent=s.title;
    const info=document.createElement("small");
    info.textContent=s.genre+" · "+s.author;

    const actions=document.createElement("div");
    actions.className="actions";
    const read=document.createElement("button");
    read.textContent="اقرأ";
    read.onclick=()=>openStory(s.id);

    const fav=document.createElement("button");
    fav.className="secondary";
    fav.textContent=favorites.includes(s.id)?"★":"☆";
    fav.onclick=()=>toggleFavorite(s.id);

    actions.append(read,fav);
    card.append(cover,title,info,actions);
    container.appendChild(card);
  });
}

function openStory(id){
  const s=stories.find(x=>x.id===id);
  if(!s)return;
  currentId=id;
  document.getElementById("readerTitle").textContent=s.title;
  document.getElementById("readerInfo").textContent=
    s.genre+" · بقلم "+s.author;
  document.getElementById("storyText").textContent=s.text;
  document.getElementById("storyText").style.fontSize=fontSize+"px";

  document.getElementById("reader").style.display="block";
  document.getElementById("sectionTitle").style.display="none";
  document.getElementById("books").style.display="none";
  document.getElementById("reader").scrollIntoView({behavior:"smooth"});
}

function closeReader(){
  document.getElementById("reader").style.display="none";
  document.getElementById("sectionTitle").style.display="";
  document.getElementById("books").style.display="grid";
  render();
}

function toggleFavorite(id){
  if(favorites.includes(id))
    favorites=favorites.filter(x=>x!==id);
  else favorites.push(id);
  save();
  if(currentId===id)
    document.querySelector('#reader .readerTools button:nth-child(3)').textContent=
      favorites.includes(id)?"★ أزيل من المفضلة":"⭐ المفضلة";
  render();
}

function changeFont(amount){
  fontSize=Math.max(14,Math.min(32,fontSize+amount*2));
  document.getElementById("storyText").style.fontSize=fontSize+"px";
}

function nextStory(){
  const index=stories.findIndex(s=>s.id===currentId);
  const next=stories[(index+1)%stories.length];
  if(next)openStory(next.id);
}

function toggleAdd(){
  const panel=document.getElementById("addPanel");
  panel.style.display=panel.style.display==="block"?"none":"block";
  panel.scrollIntoView({behavior:"smooth",block:"nearest"});
}

function addStory(){
  const title=document.getElementById("newTitle").value.trim();
  const author=document.getElementById("newAuthor").value.trim()||"كاتب مجهول";
  const genre=document.getElementById("newGenre").value;
  const text=document.getElementById("newText").value.trim();

  if(!title||!text){
    alert("اكتب عنوان القصة ومحتواها أولًا.");
    return;
  }

  stories.unshift({
    id:Date.now(),title,author,genre,text
  });
  save();

  document.getElementById("newTitle").value="";
  document.getElementById("newAuthor").value="";
  document.getElementById("newText").value="";
  document.getElementById("addPanel").style.display="none";
  currentGenre="all";
  showLibrary("all");
  alert("تم حفظ قصتك في المكتبة.");
}

function toggleTheme(){
  document.body.classList.toggle("light");
  try{
    localStorage.setItem("storyTheme",
      document.body.classList.contains("light")?"light":"dark");
  }catch(e){}
}

render();
</script>
</body>
</html>