/*
 * Copyright © 2022 Ben Petrillo. All rights reserved.
 *
 * Project licensed under the MIT License: https://www.mit.edu/~amini/LICENSE.md
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * All portions of this software are available for public use, provided that
 * credit is given to the original author(s).
 */

package dev.benpetrillo.commands.slash;

import dev.benpetrillo.types.ApplicationCommand;
import dev.benpetrillo.utils.EmbedUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class HelpCommand implements ApplicationCommand {

    private final String name = "help";
    private final String description = "Obtain a list of all available commands.";

    @Override
    public void runCommand(SlashCommandEvent event, Member member, Guild guild) {
        event.deferReply().queue(hook -> {
            try {
                hook.editOriginalEmbeds(EmbedUtil.getHelpEmbed(event.getJDA())).queue();
            } catch (PermissionException ignored) {
                MessageEmbed embed = EmbedUtil.sendDefaultEmbed("I do not have permissions to do this.", member.getJDA());
                hook.editOriginalEmbeds(embed).queue();
            } catch (Error ignored) {
                MessageEmbed embed = EmbedUtil.sendDefaultEmbed("An error occurred while running this command.", member.getJDA());
                hook.editOriginalEmbeds(embed).queue();
            }
        });
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(this.name, this.description);
    }
}
