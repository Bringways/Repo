const memories = [
  {
    icon: "📱",
    title: "Nokia 3310",
    category: "Technology",
    year: "2000",
    text: "The phone remembered for Snake, long battery life and legendary durability.",
  },
  {
    icon: "🎮",
    title: "Video Game Era",
    category: "Gaming",
    year: "1990s",
    text: "From cartridge games to neighbourhood gaming sessions.",
  },
  {
    icon: "📺",
    title: "Doordarshan Days",
    category: "TV & Movies",
    year: "1980s",
    text: "The television moments that brought families together.",
  },
  {
    icon: "🍬",
    title: "Old School Brands",
    category: "Food & Brands",
    year: "1990s",
    text: "The snacks, drinks and brands that defined childhood.",
  },
];

const categories = [
  "Technology",
  "Gaming",
  "Toys",
  "TV & Movies",
  "Music",
  "School",
  "Sports",
  "Food & Brands",
  "Fashion",
];

export default function Home() {
  return (
    <main className="min-h-screen bg-[#f4ead8] text-[#241c17]">

      {/* NAVBAR */}
      <header className="sticky top-0 z-50 border-b border-[#241c17]/10 bg-[#f4ead8]/95 backdrop-blur">
        <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-5">

          <div>
            <h1 className="text-2xl font-black tracking-[0.2em]">
              RETRO INDIA
            </h1>

            <p className="text-xs tracking-widest text-[#806c5b]">
              MEMORIES • STORIES • MARKETPLACE
            </p>
          </div>

          <nav className="hidden gap-7 text-sm font-semibold md:flex">
            <a href="#" className="hover:text-[#a64b2a]">
              Home
            </a>

            <a href="#memories" className="hover:text-[#a64b2a]">
              Memories
            </a>

            <a href="#categories" className="hover:text-[#a64b2a]">
              Categories
            </a>

            <a href="#marketplace" className="hover:text-[#a64b2a]">
              Marketplace
            </a>
          </nav>

          <button className="rounded-full bg-[#241c17] px-5 py-3 text-sm font-bold text-white hover:bg-[#a64b2a]">
            Login
          </button>

        </div>
      </header>

      {/* HERO */}
      <section className="relative overflow-hidden bg-[#a64b2a] px-6 py-24 text-white md:py-36">

        <div className="absolute -right-20 -top-20 h-72 w-72 rounded-full border-[40px] border-[#d7a35d]/30" />

        <div className="absolute -bottom-32 -left-20 h-80 w-80 rounded-full border-[50px] border-[#e5c28c]/20" />

        <div className="relative mx-auto max-w-7xl">

          <p className="mb-5 text-sm font-bold tracking-[0.35em] text-[#f5d9a6]">
            1980s • 1990s INDIA
          </p>

          <h2 className="max-w-5xl text-6xl font-black leading-[0.9] tracking-tight md:text-9xl">
            REMEMBER
            <br />
            WHEN?
          </h2>

          <p className="mt-8 max-w-2xl text-lg leading-8 text-white/85 md:text-xl">
            A digital home for the memories, technology, music, toys,
            television and everyday moments that shaped India.
          </p>

          <div className="mt-10 flex flex-wrap gap-4">

            <a
              href="#memories"
              className="rounded-full bg-[#f4ead8] px-7 py-4 font-bold text-[#241c17] hover:bg-white"
            >
              Explore Memories →
            </a>

            <a
              href="#marketplace"
              className="rounded-full border border-white/40 px-7 py-4 font-bold hover:bg-white/10"
            >
              Shop Retro
            </a>

          </div>

        </div>
      </section>

      {/* ERA SELECTOR */}
      <section className="mx-auto max-w-7xl px-6 py-14">

        <div className="flex flex-col justify-between gap-6 rounded-3xl bg-[#241c17] p-8 text-white md:flex-row md:items-center">

          <div>
            <p className="text-sm font-bold tracking-widest text-[#d7a35d]">
              CHOOSE YOUR ERA
            </p>

            <h3 className="mt-2 text-3xl font-black">
              Which India do you remember?
            </h3>
          </div>

          <div className="flex gap-3">
            <button className="rounded-full bg-[#d7a35d] px-7 py-3 font-bold text-[#241c17]">
              1980s
            </button>

            <button className="rounded-full border border-white/30 px-7 py-3 font-bold">
              1990s
            </button>
          </div>

        </div>

      </section>

      {/* MEMORIES */}
      <section id="memories" className="mx-auto max-w-7xl px-6 py-14">

        <div className="mb-10 flex items-end justify-between">

          <div>
            <p className="text-sm font-bold tracking-widest text-[#a64b2a]">
              DISCOVER
            </p>

            <h2 className="mt-2 text-4xl font-black md:text-5xl">
              Explore Memories
            </h2>
          </div>

          <a
            href="#"
            className="hidden font-bold text-[#a64b2a] md:block"
          >
            View all →
          </a>

        </div>

        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4">

          {memories.map((memory) => (
            <article
              key={memory.title}
              className="group overflow-hidden rounded-3xl border border-[#241c17]/10 bg-[#fffaf2] transition hover:-translate-y-2 hover:shadow-xl"
            >

              <div className="flex h-52 items-center justify-center bg-[#e7d1af] text-8xl">
                {memory.icon}
              </div>

              <div className="p-6">

                <div className="flex items-center justify-between">
                  <span className="rounded-full bg-[#ead9be] px-3 py-1 text-xs font-bold">
                    {memory.category}
                  </span>

                  <span className="text-xs text-[#806c5b]">
                    {memory.year}
                  </span>
                </div>

                <h3 className="mt-5 text-2xl font-black">
                  {memory.title}
                </h3>

                <p className="mt-3 text-sm leading-6 text-[#6f6258]">
                  {memory.text}
                </p>

                <button className="mt-6 font-bold text-[#a64b2a]">
                  Read memory →
                </button>

              </div>

            </article>
          ))}

        </div>

      </section>

      {/* CATEGORIES */}
      <section
        id="categories"
        className="bg-[#e4d0ad] px-6 py-20"
      >

        <div className="mx-auto max-w-7xl">

          <p className="text-sm font-bold tracking-widest text-[#a64b2a]">
            BROWSE BY CATEGORY
          </p>

          <h2 className="mt-2 text-4xl font-black md:text-5xl">
            What do you remember?
          </h2>

          <div className="mt-10 flex flex-wrap gap-3">

            {categories.map((category) => (
              <button
                key={category}
                className="rounded-full border border-[#241c17]/15 bg-[#f7eedf] px-6 py-3 font-semibold transition hover:bg-[#241c17] hover:text-white"
              >
                {category}
              </button>
            ))}

          </div>

        </div>

      </section>

      {/* TRENDING */}
      <section className="mx-auto max-w-7xl px-6 py-20">

        <div className="grid gap-8 md:grid-cols-2">

          <div className="rounded-3xl bg-[#241c17] p-10 text-white">

            <span className="text-sm font-bold tracking-widest text-[#d7a35d]">
              TRENDING NOSTALGIA
            </span>

            <h2 className="mt-5 text-4xl font-black">
              Things everyone remembers.
            </h2>

            <p className="mt-5 leading-7 text-white/70">
              Discover the objects, entertainment and everyday experiences
              that became part of India's shared memory.
            </p>

            <button className="mt-8 rounded-full bg-[#d7a35d] px-6 py-3 font-bold text-[#241c17]">
              Explore Trending
            </button>

          </div>

          <div className="rounded-3xl bg-[#c56a43] p-10 text-white">

            <span className="text-sm font-bold tracking-widest text-[#f8d8a6]">
              MEMORY OF THE DAY
            </span>

            <div className="mt-8 text-7xl">
              📼
            </div>

            <h2 className="mt-5 text-3xl font-black">
              Saturday morning cartoons
            </h2>

            <p className="mt-4 leading-7 text-white/80">
              When television schedules became part of childhood routines.
            </p>

          </div>

        </div>

      </section>

      {/* COMMUNITY */}
      <section className="bg-[#fffaf2] px-6 py-20">

        <div className="mx-auto max-w-7xl">

          <div className="max-w-2xl">

            <p className="text-sm font-bold tracking-widest text-[#a64b2a]">
              COMMUNITY STORIES
            </p>

            <h2 className="mt-2 text-4xl font-black md:text-5xl">
              Your memories belong here.
            </h2>

            <p className="mt-5 leading-7 text-[#6f6258]">
              Share photographs, stories and personal memories with people
              who grew up with the same India.
            </p>

            <button className="mt-7 rounded-full bg-[#a64b2a] px-7 py-4 font-bold text-white">
              Share Your Memory
            </button>

          </div>

        </div>

      </section>

      {/* MARKETPLACE */}
      <section
        id="marketplace"
        className="mx-auto max-w-7xl px-6 py-20"
      >

        <div className="mb-10">

          <p className="text-sm font-bold tracking-widest text-[#a64b2a]">
            MARKETPLACE
          </p>

          <h2 className="mt-2 text-4xl font-black md:text-5xl">
            Find a piece of your past.
          </h2>

        </div>

        <div className="grid gap-6 md:grid-cols-3">

          <div className="rounded-3xl bg-[#e7d1af] p-8">
            <div className="text-7xl">📱</div>
            <h3 className="mt-6 text-2xl font-black">
              Nokia 3310
            </h3>
            <p className="mt-2 text-[#6f6258]">
              Collector item
            </p>
            <p className="mt-5 text-2xl font-black">
              ₹2,499
            </p>
          </div>

          <div className="rounded-3xl bg-[#d9c1a0] p-8">
            <div className="text-7xl">🎮</div>
            <h3 className="mt-6 text-2xl font-black">
              Retro Gaming
            </h3>
            <p className="mt-2 text-[#6f6258]">
              Classic collection
            </p>
            <p className="mt-5 text-2xl font-black">
              Explore →
            </p>
          </div>

          <div className="rounded-3xl bg-[#c7ad8d] p-8">
            <div className="text-7xl">🧸</div>
            <h3 className="mt-6 text-2xl font-black">
              Vintage Toys
            </h3>
            <p className="mt-2 text-[#6f6258]">
              Collector favourites
            </p>
            <p className="mt-5 text-2xl font-black">
              Explore →
            </p>
          </div>

        </div>

      </section>

      {/* FOOTER */}
      <footer className="bg-[#241c17] px-6 py-14 text-white">

        <div className="mx-auto flex max-w-7xl flex-col justify-between gap-8 md:flex-row">

          <div>
            <h2 className="text-2xl font-black tracking-[0.2em]">
              RETRO INDIA
            </h2>

            <p className="mt-3 max-w-md text-sm leading-6 text-white/60">
              Remembering India's 1980s and 1990s through memories,
              stories and a trusted retro marketplace.
            </p>
          </div>

          <div className="text-sm text-white/60">
            <p>© 2026 Retro India</p>
            <p className="mt-2">
              Memories • Community • Marketplace
            </p>
          </div>

        </div>

      </footer>

    </main>
  );
}