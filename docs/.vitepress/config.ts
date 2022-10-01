import { defineConfig } from 'vitepress'
import { description, discord, font, github, name, releases, twitter,} from './meta'

const version = '0.0.0'

export default defineConfig({
    lang: 'zh-CN',
    title: name,
    description,
    head: [
        ['meta', { name: 'theme-color', content: '#ffffff' }],
        ['link', { rel: 'icon', href: '/logo.svg', type: 'image/svg+xml' }],
        ['meta', { property: 'og:title', content: name }],
        ['meta', { property: 'og:description', content: description }],
        ['meta', { name: 'twitter:title', content: name }],
        ['meta', { name: 'twitter:description', content: description }],
        ['meta', { name: 'twitter:card', content: 'summary_large_image' }],
        ['link', { href: font, rel: 'stylesheet' }],
        ['link', { rel: 'mask-icon', href: '/logo.svg', color: '#ffffff' }],
    ],
    lastUpdated: true,
    markdown: {
        theme: {
            light: 'vitesse-light',
            dark: 'vitesse-dark',
        },
    },
    themeConfig: {
        logo: '/logo.svg',
        editLink: {
            pattern: 'https://github.com/elonehoo/logon-tracer/tree/main/docs/:path',
            text: 'Suggest changes to this page',
        },
        socialLinks: [
            { icon: 'twitter', link: twitter },
            { icon: 'discord', link: discord },
            { icon: 'github', link: github },
        ],
        footer: {
            message: 'Released under the MIT License.',
            copyright: 'Copyright © 2022-PRESENT Elone Hoo',
        },
        nav: [
            { text: 'Guide', link: '/guide/' },
            {
                text: `v${version}`,
                items: [
                    {
                        text: '版本发布',
                        link: releases,
                    },
                ],
            },
        ],
        sidebar: {
            '/':[]
        }
    },
})